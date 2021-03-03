package indi.ayun.mingwork_all.retrofit2.bean;

import java.util.List;

public class ApiResponse<T> {
    private String responseCode;
    private String msg;
    private T obj;
    private List<T> objList;
    private int currentPage;
    private int pageSize;
    private int maxCount;
    private int maxPage;

    public ApiResponse() {
    }

    public String getResponseCode() {
        return this.responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getObj() {
        return this.obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public List<T> getObjList() {
        return this.objList;
    }

    public void setObjList(List<T> objList) {
        this.objList = objList;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getMaxCount() {
        return this.maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getMaxPage() {
        return this.maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public String toString() {
        return "ApiResponse [responseCode=" + this.responseCode + ", msg=" + this.msg + ", obj=" + this.obj + ", objList=" + this.objList + ", currentPage=" + this.currentPage + ", pageSize=" + this.pageSize + ", maxCount=" + this.maxCount + ", maxPage=" + this.maxPage + "]";
    }
}
