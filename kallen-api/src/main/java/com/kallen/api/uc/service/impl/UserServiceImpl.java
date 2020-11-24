package com.kallen.api.uc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.kallen.api.uc.dao.UserDao;
import com.kallen.api.uc.entity.UserEntity;
import com.kallen.api.uc.entity.dto.UserDTO;
import com.kallen.api.uc.entity.req.SendCodeReq;
import com.kallen.api.uc.service.UserService;
import com.kallen.common.service.impl.CrudServiceImpl;
import com.kallen.common.utils.ValidateCode;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: UserServiceImpl</p >
 * <p>Description: </p >
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
    public void sendCode(SendCodeReq sendCodeReq) {
        String mobile = sendCodeReq.getMobile();
        // 随机6位数字验证码
        String code = ValidateCode.generateTextCode(ValidateCode.TYPE_NUM_ONLY, 6, null);

        // 短信发送验证码
        Map<String, String> map = new HashMap<>();
        map.put("code", code);


    }
}
