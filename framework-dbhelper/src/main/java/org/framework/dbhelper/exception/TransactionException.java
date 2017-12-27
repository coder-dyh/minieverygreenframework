package org.framework.dbhelper.exception;

/**
 * Create by coder_dyh on 2017/12/27
 */
public class TransactionException extends SQLExecutorException {

    public TransactionException() {
        super();
    }

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }
}
