package com.kallen.api.core.exception;

import com.kallen.common.exception.ErrorCode;
import com.kallen.common.exception.KallenException;
import com.kallen.common.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>Title: KallenExceptionHandler</p >
 * <p>Description: 异常处理器</p >
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
@RestControllerAdvice
public class KallenExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(KallenExceptionHandler.class);

    /**
     * <p>处理自定义异常</p>
     *
     * @param ex                自定义异常
     * @return {@link Result}   异常信息
     * @author Kallen
     * @since 2020/11/20 13:38
    */
    @ExceptionHandler(KallenException.class)
    public Result handleKallenException(KallenException ex) {
        Result result = new Result();
        result.error(ex.getCode(), ex.getMsg());
        return result;
    }

    /**
     * <p>处理IO异常</p>
     *
     * @param ex                IO异常
     * @return {@link Result}   异常信息
     * @author Kallen
     * @since 2020/11/20 13:55
    */
    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException ex) {
        Result result = new Result();
        result.error(ErrorCode.PARAMS_GET_ERROR);
        result.setMsg(ex.getMessage());
        return result;
    }

    /**
     * <p>处理运行时异常</p>
     *
     * @param ex                运行时异常
     * @return {@link Result}   异常信息
     * @throws 
     * @author Kallen
     * @since 2020/11/20 13:56
    */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception ex) {
        logger.error(ex.getMessage(), ex);

        return new Result().error();
    }
}
