package com.kallen.common.exception;

/**
 * <p>Title: ErrorCode</p >
 * <p>Description: 错误编码，由 5 位数字组成，前 2 位为模块编码，后 3 位为业务编码</p >
 * <p>
 *    如：10001（10 代表系统模块， 001 代表业务代码）
 * </p>
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
public interface ErrorCode {
    int INTERNAL_SERVER_ERROR = 500;
    int UNAUTHORIZED = 401;
    int FORBIDDEN = 403;

    // 系统模块
    int PARAMS_GET_ERROR = 10003;
}
