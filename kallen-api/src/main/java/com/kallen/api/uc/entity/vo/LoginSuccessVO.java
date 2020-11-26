package com.kallen.api.uc.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>Title: LoginSuccessVO</p >
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
@Data
public class LoginSuccessVO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "token过期时间")
    @JsonFormat(pattern = "yyyy-dd-MM HH:mm:ss", timezone = "GTM+8")
    @DateTimeFormat(pattern = "yyyy-dd-MM HH:mm:ss")
    private Date expireTime;

    @ApiModelProperty(value = "用户信息")
    private UserVO userVO;

    @ApiModelProperty(value = "用户openID")
    private String openId;

    @ApiModelProperty(value = "是否绑定了手机号，0：没有绑定，1：已绑定")
    private Integer hasMobile;


}
