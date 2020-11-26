package com.kallen.api.uc.entity.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * <p>Title: CodeLoginReq</p >
 * <p>Description: 验证码登录-入参实体</p >
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
public class CodeLoginReq implements Serializable {

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "{user.mobile.require}")
    @Pattern(regexp = "^1\\d{10}$", message = "{user.mobile.error}")
    private String mobile;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码", required = true)
    @NotBlank(message = "{user.captcha.require}")
    private String code;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String ip;
}
