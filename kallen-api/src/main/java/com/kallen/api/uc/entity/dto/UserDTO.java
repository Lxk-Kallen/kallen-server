package com.kallen.api.uc.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>Title: UserDTO</p >
 * <p>Description: 用户-数据传输实体</p >
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
@ApiModel(value = "用户")
public class UserDTO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户编号")
    private String code;

    @ApiModelProperty(value = "用户状态，字典：A02")
    private String status;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "小鹅通用户id")
    private String xetUserId;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "性别，字典：A03")
    private Integer gender;

    @ApiModelProperty(value = "生日，格式：YYYY-MM-DD")
    private LocalDate birth;

    @ApiModelProperty(value = "手机号，仅作显示用")
    private String mobile;

    @ApiModelProperty(value = "所在城市")
    private String city;

    @ApiModelProperty(value = "注册ip")
    private String regIp;

    @ApiModelProperty(value = "注册时间")
    @JsonFormat(pattern = "yyyy-dd-MM HH:mm:ss", timezone = "GTM+8")
    @DateTimeFormat(pattern = "yyyy-dd-MM HH:mm:ss")
    private LocalDateTime regTime;

    @ApiModelProperty(value = "最近一次登录ip")
    private String lastLoginIp;

    @ApiModelProperty(value = "最近一次登录时间")
    @JsonFormat(pattern = "yyyy-dd-MM HH:mm:ss", timezone = "GTM+8")
    @DateTimeFormat(pattern = "yyyy-dd-MM HH:mm:ss")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否删除，字典：A01")
    private Integer deleted;

    @ApiModelProperty(value = "修改者")
    private Long updater;

    @ApiModelProperty(value = "用户来源，字典：A25")
    private String source;

    @ApiModelProperty(value = "最后修改时间")
    @JsonFormat(pattern = "yyyy-dd-MM HH:mm:ss", timezone = "GTM+8")
    @DateTimeFormat(pattern = "yyyy-dd-MM HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "微信号")
    private String wechatName;
}
