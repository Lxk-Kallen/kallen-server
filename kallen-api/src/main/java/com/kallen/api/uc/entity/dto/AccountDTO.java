package com.kallen.api.uc.entity.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kallen.common.entity.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class AccountDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

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
    private Boolean deleted;

    /**
     * 修改者
     */
    private Long updater;

    /**
     * 最后修改时间
     */
    @JsonFormat(pattern = "yyyy-dd-MM HH:mm:ss", timezone = "GTM+8")
    @DateTimeFormat(pattern = "yyyy-dd-MM HH:mm:ss")
    private Date updateTime;


}
