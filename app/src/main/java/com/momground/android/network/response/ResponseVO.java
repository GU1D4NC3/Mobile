package com.momground.android.network.response;


public class ResponseVO {

    private boolean success;
    private Object data;
    private java.lang.Error error;

    public ResponseVO() {

    }

    public ResponseVO(boolean success, Object data, java.lang.Error error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public java.lang.Error getError() {
        return error;
    }

    public void setError(java.lang.Error error) {
        this.error = error;
    }
}
