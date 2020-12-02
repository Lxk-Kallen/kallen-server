package com.kallen.api.uc.entity.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * <p>Title: PasswordLoginReq</p >
 * <p>Description: 密码登录</p >
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
public class PasswordLoginReq {

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "{user.mobile.require}")
    @Pattern(regexp = "^1\\d{10}$", message = "{user.mobile.error}")
    private String mobile;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String ip;
}
