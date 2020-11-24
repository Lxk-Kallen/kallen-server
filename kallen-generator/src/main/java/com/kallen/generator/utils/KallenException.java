package com.kallen.generator.utils;

import lombok.Data;

/**
 * <p>Title: KallenException</p >
 * <p>Description: 自定义异常</p >
 * <p>Copyright: Kallen. Copyright(c) 2020</p >
 * <link>http://www.buqu.icu</link>
 * <p>版权所有，侵权必究！</p >
 *
 * @author Kallen
 * @version 1.0.0
 * <pre>History:
 *       2020/11/20    Kallen    Created
 * </pre>
 */
@Data
public class KallenException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;

    public KallenException(int code) {
        this.code = code;
        this.msg = MessageUtils.getMessage(code);
    }

    public KallenException(int code, String... params) {
        this.code = code;
        this.msg = MessageUtils.getMessage(code, params);
    }

    public KallenException(int code, Throwable e) {
        super(e);
        this.code = code;
        this.msg = MessageUtils.getMessage(code);
    }

    public KallenException(int code, Throwable e, String... params) {
        super(e);
        this.code = code;
        this.msg = MessageUtils.getMessage(code, params);
    }

    public KallenException(String msg) {
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = msg;
    }

    public KallenException(String msg, Throwable e) {
        super(e);
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = msg;
    }

    public KallenException(String msg, int code){
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
