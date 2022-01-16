package com.jitgad.bjitgad.Utilities;

/**
 *
 * @author jorge
 */
public class ResponseData {
    
    public String message;
    
    public Boolean flag;
    
    public Object data;

    public ResponseData() {
    }

    public ResponseData(String message, Boolean flag, Object data) {
        this.message = message;
        this.flag = flag;
        this.data = data;
    }

    public ResponseData(String message, Boolean flag) {
        this.message = message;
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
    
}
