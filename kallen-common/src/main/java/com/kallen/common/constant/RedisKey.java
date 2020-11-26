package com.kallen.common.constant;

/**
 * <p>Title: RedisKey</p >
 * <p>Description: RedisKey 说明</p >
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
public interface RedisKey {

    /**
     * <p>手机验证码</p>
     *
     * @param mobile            手机号
     * @param code              验证码
     * @return {@link String}   格式："uc:mobile:code:{mobile}:{code}"
     * @author Kallen
     * @since 2020/11/26 11:15
    */
    static String getUserVerifyCode(String mobile, String code) {
        return "uc:verifyCOde:" + mobile + ":" + code;
    }

    /**
     * <p>用户token</p>
     *
     * @param userId            用户ID
     * @return {@link String}   token："uc:token:u:{userId}"
     *                          value：token+"|"+expireDate
     * @author Kallen
     * @since 2020/11/26 11:15
    */
    static String getTokenKeyByUserId(Long userId) {
        return "uc:token:u:" + userId;
    }

    /**
     * <p>根据用户token生成token</p>
     *
     * @param token             用户token
     * @return {@link String}   token："uc:token:t:{token}"
     *                          value：userId+"|"+expireDate
     * @author Kallen
     * @since 2020/11/26 11:23
    */
    static String getTokenKeyByToken(String token) {
        return "uc:token:t:" + token;
    }
}
