package com.kallen.api.uc.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kallen.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>Title: UserEntity</p >
 * <p>Description: 用户-数据实体</p >
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
@EqualsAndHashCode(callSuper = false)
@TableName("uc_user")
public class UserEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private String code;

    /**
     * 用户状态，字典：A02
     */
    private String status;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 小鹅通用户id
     */
    private String xetUserId;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别，字典：A03
     */
    private Integer gender;

    /**
     * 生日，格式：YYYY-MM-DD
     */
    private LocalDate birth;

    /**
     * 手机号，仅作显示用
     */
    private String mobile;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 注册ip
     */
    private String regIp;

    /**
     * 注册时间
     */
    private Date regTime;

    /**
     * 最近一次登录ip
     */
    private String lastLoginIp;

    /**
     * 最近一次登录时间
     */
    private Date lastLoginTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除，字典：A01
     */
    @TableLogic
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    private Boolean deleted;

    /**
     * 修改者
     */
    private Long updater;

    /**
     * 用户来源，字典：A25
     */
    private String source;

    /**
     * 最后修改时间
     */
    private Date updateTime;

    /**
     * 微信号
     */
    private String wechatName;

}
