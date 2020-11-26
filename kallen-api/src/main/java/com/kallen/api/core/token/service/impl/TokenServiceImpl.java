package com.kallen.api.core.token.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.kallen.api.core.token.service.TokenService;
import com.kallen.api.core.utils.RedisUtil;
import com.kallen.common.constant.RedisKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>Title: TokenServiceImpl</p >
 * <p>Description: 用户token-服务层接口实现类</p >
 * <p>Copyright: Kallen. Copyright(c) 2020</p >
 * <link>http://www.buqu.icu</link>
 * <p>版权所有，侵权必究！</p >
 *
 * @author Kallen
 * @version 1.0.0
 * <pre>History:
 *       2020/11/26    Kallen    Created
 * </pre>
 * @mail LXK00515@163.com
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private RedisUtil redisUtil;

    @Value("${token.expireMinutes:150}")
    private Integer expireMinutes;

    /**
     * <p>根据用户ID，创建Token</p>
     *
     * @param userId            用户ID
     * @return {@link String}   格式为：token+"|"+expireDate
     * @author Kallen
     * @since 2020/11/26 11:13
     */
    @Override
    public String createToken(Long userId) {
        String userTokenKey = RedisKey.getTokenKeyByUserId(userId);
        // 生成token
        String token = generateToken();
        // 根据token生成TokenKey
        String tokenToToken = RedisKey.getTokenKeyByToken(token);

        String expireDateStr = DateUtil.offsetMinute(DateUtil.date(), expireMinutes).toString();

        String cacheStr = token + "|" + expireDateStr;

        redisUtil.set(userTokenKey, token + "|" + expireDateStr, expireMinutes, TimeUnit.MINUTES);
        redisUtil.set(tokenToToken, userId + "|" + expireMinutes, expireMinutes, TimeUnit.MINUTES);

        return cacheStr;
    }

    /**
     * <p>检测token是否存在</p>
     *
     * @param token             用户token
     * @return {@link Boolean}  是否存在
     * @author Kallen
     * @since 2020/11/26 11:32
     */
    @Override
    public Boolean isTokenExist(String token) {
        String tokenByToken = RedisKey.getTokenKeyByToken(token);
        return redisUtil.hasKey(tokenByToken);
    }

    /**
     * <p>如果此token存在，则返回用户的userID</p>
     *
     * @param token             用户token
     * @return {@link Long}     用户ID
     * @author Kallen
     * @since 2020/11/26 12:12
     */
    @Override
    public Long getUserIdByToken(String token) {
        String tokeToTokenKey = RedisKey.getTokenKeyByToken(token);
        if (isTokenExist(token)) {
            String cacheValue = redisUtil.get(tokeToTokenKey);
            if (StringUtils.isNotBlank(cacheValue)) {
                return Long.parseLong(StringUtils.split(cacheValue, "|")[0]);
            }
        }
        return null;
    }

    /**
     * <p>设置token过期</p>
     *
     * @param userId           用户ID
     * @author Kallen
     * @since 2020/11/26 12:17
     */
    @Override
    public void expireToken(Long userId) {
        String uk = RedisKey.getTokenKeyByUserId(userId);
        if (redisUtil.hasKey(uk)) {
            String ukv = redisUtil.get(uk);
            if (StringUtils.isNotBlank(ukv)) {
                String token = StringUtils.split(ukv, "|")[0];
                String tk = RedisKey.getTokenKeyByToken(token);
                if (redisUtil.hasKey(tk)) {
                    redisUtil.delete(tk);
                }
            }
            redisUtil.delete(uk);
        }
    }

    //<editor-fold desc="私有方法">

    /**
     * <p>生成token</p>
     *
     * @return {@link String}       UUID
     * @author Kallen
     * @since 2020/11/26 11:20
    */
    private String generateToken() {
        return IdUtil.randomUUID().replace("-", "");
    }

    //</editor-fold>
}
