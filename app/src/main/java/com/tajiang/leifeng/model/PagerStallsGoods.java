package com.tajiang.leifeng.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admins on 2016/11/2.
 */
public class PagerStallsGoods<T> implements Serializable {

    private int totalPage;		//总页数
    private int totalCount;	//总记录数
    private int currentPage;	//当前页
    private int pagesize;		//每页的数量
    private int firstPage;
    private int prePage;
    private int nextPage;
    private int lastPage;
    private List<T> list;

    public List<T> getList() {
        return list;
    }
    public void setList(List<T> list) {
        this.list = list;
    }


    public int getTotalPage() {
        return totalPage;
    }
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getPagesize() {
        return pagesize;
    }
    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
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

    /**
     * 分页处理
     * @param current_page 当前页
     * @param page_size 每页的数量
     * @param total_count 总记录数
     */
    public void paging(int currentPage, int pagesize, int totalCount){
        this.currentPage = currentPage;
        this.pagesize = pagesize;
        this.totalCount = totalCount;

        if(currentPage < 1){
            this.currentPage = 1;
        }

        this.totalPage = (this.totalCount + pagesize - 1)/pagesize;
        this.firstPage =1;
        this.lastPage = totalPage;

        if(this.currentPage > 1){
            this.prePage = this.currentPage - 1;
        }else{
            this.prePage = 1;
        }

        if(this.currentPage < totalPage){
            this.nextPage = this.currentPage + 1;
        }else{
            this.nextPage = totalPage;
        }
    }
    @Override
    public String toString() {
        return "Pager [totalPage=" + totalPage + ", totalCount=" + totalCount
                + ", currentPage=" + currentPage + ", pagesize=" + pagesize
                + ", firstPage=" + firstPage + ", prePage=" + prePage
                + ", nextPage=" + nextPage + ", lastPage=" + lastPage
                + ", list="
                + list + "]";
    }


}
