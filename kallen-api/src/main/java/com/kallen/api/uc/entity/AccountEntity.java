package com.kallen.api.uc.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kallen.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uc_account")
public class AccountEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识,如手机号码、unionId等
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户id,外键
     */
    private Long userId;

    /**
     * 账户类型，字典：A04
     */
    private String type;

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
     * 最后修改时间
     */
    private Date updateTime;


}
