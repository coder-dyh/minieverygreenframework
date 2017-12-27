package org.framework.dbhelper.exception;

/**
 * Create by coder_dyh on 2017/12/27
 */
public class CloseException extends SQLExecutorException {

    public CloseException() {
        super();
    }

    public CloseException(String message) {
        super(message);
    }

    public CloseException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloseException(Throwable cause) {
        super(cause);
    }
}
