package com.kallen.api.core.token.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>Title: TokenDTO</p >
 * <p>Description: 用户token-数据传输实体</p >
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
@Data
public class TokenDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;
}
