package com.kallen.api.uc.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.kallen.api.core.token.service.TokenService;
import com.kallen.api.core.utils.RedisUtil;
import com.kallen.api.message.service.SmsService;
import com.kallen.api.uc.dao.UserDao;
import com.kallen.api.uc.entity.UserEntity;
import com.kallen.api.uc.entity.dto.AccountDTO;
import com.kallen.api.uc.entity.dto.AuthUserAccountDTO;
import com.kallen.api.uc.entity.dto.UserDTO;
import com.kallen.api.uc.entity.req.CodeLoginReq;
import com.kallen.api.uc.entity.req.SendCodeReq;
import com.kallen.api.uc.entity.vo.LoginSuccessVO;
import com.kallen.api.uc.entity.vo.UserVO;
import com.kallen.api.uc.service.AccountService;
import com.kallen.api.uc.service.UserService;
import com.kallen.common.constant.DictCode;
import com.kallen.common.constant.RedisKey;
import com.kallen.common.constant.SmsConstant;
import com.kallen.common.exception.ErrorCode;
import com.kallen.common.exception.KallenException;
import com.kallen.common.service.impl.CrudServiceImpl;
import com.kallen.common.utils.ConvertUtils;
import com.kallen.common.utils.ValidateCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title: UserServiceImpl</p >
 * <p>Description: 用户-服务层接口实现类</p >
 * <p>Copyright: Kallen. Copyright(c) 2020</p >
 * <link>http://www.buqu.icu</link>
 * <p>版权所有，侵权必究！</p >
 *
 * @author Kallen
 * @version 1.0.0
 * <pre>History:
 *       2020/11/24    Kallen    Created
 * </pre>
 * @mail LXK00515@163.com
 */
@Service
public class UserServiceImpl extends CrudServiceImpl<UserDao, UserEntity, UserDTO> implements UserService {

    @Autowired
    private SmsService smsService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private Snowflake snowflake;

    @Override
    protected QueryWrapper<UserEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");

        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);
        return wrapper;
    }

    /**
     * <p>发送手机验证码，5分钟内有效</p>
     *
     * @param sendCodeReq       入参实体
     * @author Kallen
     * @since 2020/11/24 14:37
    */
    @Override
    public String sendCode(SendCodeReq sendCodeReq) {
        String mobile = sendCodeReq.getMobile();
        // 随机6位数字验证码
        String verifyCode = ValidateCode.generateTextCode(ValidateCode.TYPE_NUM_ONLY, 6, null);

        // 短信发送验证码
        Map<String, String> map = new HashMap<>();
        map.put("code", verifyCode);

        smsService.send(SmsConstant.VERIFY_CODE, mobile, JSON.toJSONString(map));

        // 缓存验证码 key：mobile，value：code
        // 过期时间：5分钟
        String key = RedisKey.getUserVerifyCode(mobile, verifyCode);
        redisUtil.set(key, verifyCode, 5, TimeUnit.MINUTES);

        return verifyCode;
    }

    /**
     * <p>验证码登录</p>
     *
     * @param codeLoginReq              入参实体
     * @return {@link LoginSuccessVO}   用户基本信息，包含token信息
     * @author Kallen
     * @since 2020/11/24 17:38
     */
    @Override
    @Transactional(rollbackFor = KallenException.class)
    public LoginSuccessVO codeLogin(CodeLoginReq codeLoginReq) {
        String code = codeLoginReq.getCode();
        String mobile = codeLoginReq.getMobile();

        // 手机号验证码校验
        checkVerifyCode(mobile, code, false);

        // 根据手机号登录
        LoginSuccessVO loginSuccessVO = doLoginByMobile(mobile);

        return loginSuccessVO;
    }

    /**
     * <p>通过userId查询用户信息</p>
     *
     * @param userId            用户ID
     * @return {@link UserDTO}  用户信息
     * @author Kallen
     * @since 2020/11/26 15:32
     */
    @Override
    public UserDTO getById(Long userId) {
        UserEntity userEntity = baseDao.selectById(userId);
        return userEntity == null ? null : ConvertUtils.sourceToTarget(userEntity, UserDTO.class);
    }

    /**
     * <p>获取用户账户，并登录</p>
     *
     * @param authUserAccountDTO        如果授权登录后获取到的信息，则包含账户主体和基本用户信息，其中账户主体至少需要包含account及type，且deleted为0
     *                                  如果为手机号直接登录，则账户主体为手机账户
     * @return {@link LoginSuccessVO}   用户登录后的信息
     * @author Kallen
     * @since 2020/11/24 18:05
    */
    @Transactional(rollbackFor = KallenException.class)
    public LoginSuccessVO doLoginByAccount(AuthUserAccountDTO authUserAccountDTO) {

        // 根据授权信息获取用户
        UserDTO userDTO = createUserFromOauth(authUserAccountDTO);
        if (userDTO == null) {
            throw new KallenException("登录失败，信息处理失败");
        }

        // 检查用户状态
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(DictCode.USER_STATUS.BAND.getValue(), userDTO.getStatus())) {
            throw new KallenException("用户已被禁用！");
        }

        // 登录将会使原来的token失效
        tokenService.expireToken(userDTO.getId());

        // 拿到userDTO，生成token
        String tokenStr = tokenService.createToken(userDTO.getId());
        String[] ss = StringUtils.split(tokenStr, "|");

        // 合并属性
        LoginSuccessVO loginSuccessVO = new LoginSuccessVO();
        loginSuccessVO.setToken(ss[0]);
        loginSuccessVO.setExpireTime(DateTime.of((ss[1]), DatePattern.NORM_DATE_PATTERN).toJdkDate());

        UserVO userVO = ConvertUtils.sourceToTarget(userDTO, UserVO.class);
        loginSuccessVO.setUserVO(userVO);

        return loginSuccessVO;
    }


    /**
     * <p>通过手机号查询用户信息，并按UserID缓存</p>
     *
     * @param mobile           手机号
     * @return {@link UserDTO}  用户信息
     * @author Kallen
     * @since 2020/11/24 18:24
    */
    public UserDTO getByMobile(String mobile) {
        Assert.notBlank(mobile);
        return baseDao.getByAccount(mobile, DictCode.ACCOUNT_TYPE.MOBILE.getValue());
    }

    /**
     * <p>通过unionId获取用户信息</p>
     *
     * @param unionId
     * @return {@link UserDTO}      用户信息
     * @author Kallen
     * @since 2020/11/26 12:37
     */
    public UserDTO getByUnionId(String unionId) {
        Assert.notBlank(unionId);
        return baseDao.getByAccount(unionId, DictCode.ACCOUNT_TYPE.WE_CHAT.getValue());
    }

    /**
     * <p>根据openId获取用户信息</p>
     *
     * @param openId
     * @return {@link UserDTO}
     * @throws 
     * @author Kallen
     * @since 2020/11/26 12:42
    */
    public UserDTO getByOpenId(String openId) {
        Assert.notBlank(openId);
        return baseDao.getByAccount(openId, DictCode.ACCOUNT_TYPE.OPEN_ID.getValue());
    }


    //<editor-fold desc="私有方法">

    /**
     * <p>手机号验证码校验</p>
     *
     * @param mobile        手机号
     * @param code          验证码
     * @param deleteKey             如果验证通过，是否删除缓存key
     * @author Kallen
     * @since 2020/11/24 17:41
    */
    private void checkVerifyCode(String mobile, String code, boolean deleteKey) {
        String key = RedisKey.getUserVerifyCode(mobile, code);
        if (!redisUtil.hasKey(key)) {
            throw new KallenException("验证码错误", ErrorCode.CAPTCHA_ERROR);
        }

        if (deleteKey) {
            redisUtil.delete(key);
        }
    }

    /**
     * <p>验证码验证通过后，根据手机号登录</p>
     *
     * @param mobile                    手机号
     * @return {@link LoginSuccessVO}   用户基本信息，包含token
     * @author Kallen
     * @since 2020/11/24 17:44
    */
    private LoginSuccessVO doLoginByMobile(String mobile) {
        AuthUserAccountDTO authUserAccountDTO = new AuthUserAccountDTO();

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccount(mobile);
        accountDTO.setDeleted(Boolean.FALSE);
        accountDTO.setType(DictCode.ACCOUNT_TYPE.MOBILE.getValue());
        authUserAccountDTO.setAccountDTO(accountDTO);

        UserDTO userDTO = new UserDTO();
        userDTO.setMobile(mobile);
        authUserAccountDTO.setUserDTO(userDTO);

        return doLoginByAccount(authUserAccountDTO);
    }

    /**
     * <p>根据授权信息获取用户</p>
     *
     * @param authUserAccountDTO
     * @return {@link UserDTO}      用户基本信息
     * @author Kallen
     * @since 2020/11/24 18:11
    */
    private UserDTO createUserFromOauth(AuthUserAccountDTO authUserAccountDTO) {
        if (authUserAccountDTO == null || authUserAccountDTO.getAccountDTO() == null || StringUtils.isBlank(authUserAccountDTO.getAccountDTO().getAccount())) {
            throw new KallenException("授权信息不能为空");
        }

        UserDTO userInfo = authUserAccountDTO.getUserDTO();
        AccountDTO accountInfo = authUserAccountDTO.getAccountDTO();
        AccountDTO openIdAccountInfo = authUserAccountDTO.getOpenIdAccountDTO();

        UserDTO unionIdUserDTO = null;
        UserDTO openIdUserDTO = null;
        UserDTO mobileUserDTO = null;

        // 手机登录
        if (accountInfo != null && StringUtils.endsWithIgnoreCase(DictCode.ACCOUNT_TYPE.MOBILE.getValue(), accountInfo.getType())) {
            mobileUserDTO = getByMobile(accountInfo.getAccount());
            if (mobileUserDTO != null) {
                return mobileUserDTO;
            }

            // 如果根据手机号查询不到用户信息，就创建用户
            userInfo.setSource(DictCode.ORDER_SOURCE.JSAPI.getValue());
            UserDTO userDTO = createUserDTO(userInfo);
            accountInfo.setUserId(userDTO.getId());
            createAccount(accountInfo);
            return userDTO;
        }

        // 微信授权登录，unionId和openId必须都存在
        if (accountInfo != null
                && org.apache.commons.lang3.StringUtils.equalsIgnoreCase(DictCode.ACCOUNT_TYPE.WE_CHAT.getValue(), accountInfo.getType())
                && openIdAccountInfo != null) {
           unionIdUserDTO = getByUnionId(accountInfo.getAccount());
           if (unionIdUserDTO != null) {
               return unionIdUserDTO;
           }

           // unionId不存在，则看openId是否存在，存在的话直接补全unionId
            openIdUserDTO = getByOpenId(openIdAccountInfo.getAccount());
           if (openIdUserDTO != null) {
               // 补全或是替换掉unionId，此处替换的原因是可能用户先从"其他平台"同步过来，其unionId不一致
               // unionId以授权登录的为准
               addOrReplaceUnionId(openIdUserDTO, accountInfo.getAccount());
               return openIdUserDTO;
           }

           // 两个都不存在，直接创建新用户
            userInfo.setSource(DictCode.ORDER_SOURCE.JSAPI.getValue());
            UserDTO userDTO = createUserDTO(userInfo);
            accountInfo.setUserId(userDTO.getId());
            createAccount(accountInfo);
            return userDTO;
        }
        return null;
    }

    /**
     * <p>补全或是替换掉unionId</p>
     *
     * @param userDTO     用户信息
     * @param account     手机号
     * @author Kallen
     * @since 2020/11/26 12:46
    */
    private void addOrReplaceUnionId(UserDTO userDTO, String account) {
        if (userDTO == null) {
            return;
        }
        AccountDTO unionIdAccount = accountService.queryAccountByUserIdAndType(userDTO.getId(), DictCode.ACCOUNT_TYPE.WE_CHAT.getValue());
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccount(account);
        accountDTO.setUserId(userDTO.getId());
        accountDTO.setType(DictCode.ACCOUNT_TYPE.WE_CHAT.getValue());
        if (unionIdAccount != null) {
            createAccount(accountDTO);
            return;
        }
        accountDTO.setId(unionIdAccount.getId());
        accountService.update(accountDTO);
    }

    /**
     * <p>创建账户信息</p>
     *
     * @param accountInfo       UserID，account，type必须存在
     * @author Kallen
     * @since 2020/11/24 18:39
    */
    private AccountDTO createAccount(AccountDTO accountInfo) {
        if (accountInfo == null || accountInfo.getUserId() == null || StringUtils.isBlank(accountInfo.getAccount()) || StringUtils.isBlank(accountInfo.getType())) {
            throw new KallenException("创建账户错误，未找到用户");
        }

        AccountDTO accountDTO = new AccountDTO();
        BeanUtils.copyProperties(accountInfo, accountDTO);
        accountDTO.setId(snowflake.nextId());
        accountDTO.setDeleted(false);
        accountService.save(accountDTO);
        return accountDTO;
    }

    /**
     * <p>创建用户</p>
     *
     * @param userDTO          用户信息
     * @return {@link UserDTO}  用户信息
     * @author Kallen
     * @since 2020/11/24 18:32
    */
    private UserDTO createUserDTO(UserDTO userDTO) {

        // 创建用户
        UserDTO user = (userDTO == null) ? new UserDTO() : userDTO;
        long userId = snowflake.nextId();
        user.setId(userId);
        // TODO 生成编码需要统一
        user.setCode("K" + ValidateCode.generateTextCode(ValidateCode.TYPE_NUM_ONLY, 5, null));
        user.setDeleted(false);
        user.setRegTime(new Date());
        user.setMobile(userDTO.getMobile());
        user.setSource(userDTO.getSource());
        user.setStatus(DictCode.USER_STATUS.NORMAL.getValue());
        save(user);

        return user;
    }

    //</editor-fold>
}
