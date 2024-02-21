package com.momground.android.network.response;


public class Error {
    
    private ErrorCode code;
    private String msg;
    private Object data;
    
    public Error() {
    
    }
    
    public Error(ErrorCode code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    
    public ErrorCode getCode() {
        return code;
    }
    
    public void setCode(ErrorCode code) {
        this.code = code;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public Object getData() {
        return data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
}
