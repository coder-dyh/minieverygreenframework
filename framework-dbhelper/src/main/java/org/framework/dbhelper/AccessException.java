package org.framework.dbhelper;

public class AccessException extends Exception{

    private int statusCode;
    private String message;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
