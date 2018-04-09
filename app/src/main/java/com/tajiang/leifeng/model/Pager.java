package com.tajiang.leifeng.model;

import java.io.Serializable;
import java.util.List;

public class Pager<T> implements Serializable {

    private int totalPageCount;        //总页数
    private int totalCount;    //总记录数
    private int page;    //当前页
    private int pageSize;        //每页的数量
    private int firstPage;
    private int prePage;
    private int nextPage;
    private int lastPage;
    private boolean isFirst;
    private boolean isLast;
    private List<T> list;

    private String navigation; // 分页导航

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean isLast) {
        this.isLast = isLast;
    }

    public String getNavigation() {
        return navigation;
    }

    public void setNavigation(String navigation) {
        this.navigation = navigation;
    }

    /**
     * 分页处理
     *
     * @param currentPage 当前页
     * @param pagesize    每页的数量
     * @param totalCount  总记录数
     */
    public void paging(int currentPage, int pagesize, int totalCount) {
        this.page = currentPage;
        this.pageSize = pagesize;
        this.totalCount = totalCount;

        if (currentPage < 1) {
            this.page = 1;
        }

        this.totalPageCount = (this.totalCount + pagesize - 1) / pagesize;
        this.firstPage = 1;
        this.lastPage = totalPageCount;

        if (this.page > 1) {
            this.prePage = this.page - 1;
        } else {
            this.prePage = 1;
        }

        if (this.page < totalPageCount) {
            this.nextPage = this.page + 1;
        } else {
            this.nextPage = totalPageCount;
        }

        if (this.page <= 1) {
            this.isFirst = true;
        } else {
            this.isFirst = false;
        }

        if (this.page >= totalPageCount) {
            this.isLast = true;
        } else {
            this.isLast = false;
        }
    }

    @Override
    public String toString() {
        return "Pager [totalPage=" + totalPageCount + ", totalCount=" + totalCount
                + ", currentPage=" + page + ", pagesize=" + pageSize
                + ", firstPage=" + firstPage + ", prePage=" + prePage
                + ", nextPage=" + nextPage + ", lastPage=" + lastPage
                + ", isFirst=" + isFirst + ", isLast=" + isLast + ", list="
                + list + ", navigation=" + navigation + "]";
    }


}
