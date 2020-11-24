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
     * 手机验证码 "uc:mobile:code:{mobile}:{code}"
     */
    static String getUserVerifyCode(String mobile, String code) {
        return "uc:verifyCOde:" + mobile + ":" + code;
    }

}
