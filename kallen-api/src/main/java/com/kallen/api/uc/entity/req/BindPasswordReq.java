package com.kallen.api.uc.entity.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>Title: BindPassworkReq</p >
 * <p>Description: 绑定密码</p >
 * <p>Copyright: Kallen. Copyright(c) 2020</p >
 * <link>http://www.buqu.icu</link>
 * <p>版权所有，侵权必究！</p >
 *
 * @author Kallen
 * @version 1.0.0
 * <pre>History:
 *       2020/12/2    Kallen    Created
 * </pre>
 * @mail LXK00515@163.com
 */
@Data
public class BindPasswordReq {

    /**
     * 用户Id
     */
    @ApiModelProperty(value = "用户Id", hidden = true)
    private Long userId;

    /**
     * 密码
     */
    private String password;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String ip;
}
