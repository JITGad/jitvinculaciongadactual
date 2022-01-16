package com.jitgad.bjitgad.Utilities;

/**
 *
 * @author jorge
 */
public class ResponseDataPage {
    
    public String message;
    
    public int countingpage;
    
    public Boolean flag;
    
    public Object data;
    
    public int TotalPages = (countingpage / 10) + 1;

    public ResponseDataPage() {
    }

    public ResponseDataPage(String message, int countingpage, Boolean flag, Object data) {
        this.message = message;
        this.countingpage = countingpage;
        this.flag = flag;
        this.data = data;
    }

    public ResponseDataPage(String message, int countingpage, Boolean flag) {
        this.message = message;
        this.countingpage = countingpage;
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCountingpage() {
        return countingpage;
    }

    public void setCountingpage(int countingpage) {
        this.countingpage = countingpage;
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

    public int getTotalPages() {
        return TotalPages;
    }

    public void setTotalPages(int TotalPages) {
        this.TotalPages = TotalPages;
    }
    
    
    
    
}
