package com.sijia3.base;


import java.io.Serializable;

/**
 * @author sijia3
 * @date 2019/12/19 15:53
 */
public class Response implements Serializable{

    private String requestId;
    private Object result;
    private Exception exception;


    public boolean hasException() {
        return exception != null;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
