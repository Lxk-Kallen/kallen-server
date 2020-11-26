package com.kallen.api.uc.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>Title: AuthUserAccountDTO</p >
 * <p>Description: 第三方授权用户信息</p >
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
@Data
public class AuthUserAccountDTO implements Serializable {

    private UserDTO userDTO;
    private AccountDTO accountDTO;

    // 公众号授权时使用
    private AccountDTO openIdAccountDTO;

    // 公众号授权时返回
    private String openId;
}
