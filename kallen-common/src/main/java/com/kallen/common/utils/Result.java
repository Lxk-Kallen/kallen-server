package com.kallen.common.utils;

import com.kallen.common.exception.ErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Title: Result<T></></p >
 * <p>Description: 响应数据</p >
 * <p>Copyright: Kallen. Copyright(c) 2020</p >
 * <link>http://www.buqu.icu</link>
 * <p>版权所有，侵权必究！</p >
 *
 * @author Kallen
 * @version 1.0.0
 * <pre>History:
 *       2020/11/20    admin    Created
 * </pre>
 */
@ApiModel(value = "响应")
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编码：0 表示成功，其他值表示失败
     */
    @ApiModelProperty(value = "编码：0 表示成功，其他值表示失败")
    private int code = 0;

    /**
     * 消息内容
     */
    @ApiModelProperty(value = "消息内容")
    private String msg = "success";

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T date;

    public Result<T> ok(T date) {
        this.setDate(date);
        return this;
    }

    public boolean success() {
        return code == 0 ? true : false;
    }

    public Result<T> error() {
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = MessageUtils.getMessage(this.code);
        return this;
    }

    public Result<T> error(int code) {
        this.code = code;
        this.msg = MessageUtils.getMessage(this.code);
        return this;
    }

    public Result<T> error(int code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public Result<T> error(String msg) {
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = msg;
        return  this;
    }
}

