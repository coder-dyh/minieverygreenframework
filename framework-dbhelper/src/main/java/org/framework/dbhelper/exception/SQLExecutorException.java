package org.framework.dbhelper.exception;

/**
 * Create by coder_dyh on 2017/12/27
 */
public class SQLExecutorException extends RuntimeException{

    public SQLExecutorException() {
        super();
    }

    public SQLExecutorException(String message) {
        super(message);
    }

    public SQLExecutorException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLExecutorException(Throwable cause) {
        super(cause);
    }
}
