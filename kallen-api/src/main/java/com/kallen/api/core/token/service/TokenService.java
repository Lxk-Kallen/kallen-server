package com.kallen.api.core.token.service;

/**
 * <p>Title: TokenService</p >
 * <p>Description: 用户token-服务层</p >
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
public interface TokenService {

    /**
     * <p>根据用户ID，创建Token</p>
     *
     * @param userId            用户ID
     * @return {@link String}   格式为：token+"|"+expireDate
     * @author Kallen
     * @since 2020/11/26 11:13
    */
    String createToken(Long userId);

    /**
     * <p>检测token是否存在</p>
     *
     * @param token             用户token
     * @return {@link Boolean}  是否存在
     * @author Kallen
     * @since 2020/11/26 11:32
    */
    Boolean isTokenExist(String token);

    /**
     * <p>如果此token存在，则返回用户的userID</p>
     *
     * @param token             用户token
     * @return {@link Long}     用户ID
     * @author Kallen
     * @since 2020/11/26 12:12
    */
    Long getUserByToken(String token);

    /**
     * <p>设置token过期</p>
     *
     * @param userId           用户ID
     * @author Kallen
     * @since 2020/11/26 12:17
    */
    void expireToken(Long userId);
}
