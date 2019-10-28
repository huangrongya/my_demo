package com.cbest.demo.exception;

import java.util.Formatter;

/**
 * @Author royle.huang
 * @Date 17:08 2019/10/11
 * @Description 自定义异常父类
 **/
public class KnownRuntimeException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */

    public KnownRuntimeException(String message) {
        super(message);
    }

    public KnownRuntimeException(String format , Object ... args){
        super(new Formatter().format(format, args).toString());
    }

    public KnownRuntimeException(String format , Throwable throwable , Object ... args){
        super(new Formatter().format(format, args).toString(),throwable);
    }
}
