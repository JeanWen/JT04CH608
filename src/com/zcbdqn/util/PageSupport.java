package com.zcbdqn.util;

import java.util.List;

public class PageSupport<T> {

    /**
     * 页码
     */
    private Integer pageIndex;

    /**
     * 页面容量
     */
    private Integer pageSize;

    /**
     * 总数量
     */
    private Integer totalCount;

    /**
     * 页数
     */
    private Integer pageCount;

    /**
     * 查询得到数据集合
     */
    private List<T> list;

    public Integer getPageIndex() {
        return pageIndex;
    }

    /**
     * 页码应该1-总页数之间    1>=pageIndex<=pageCount
     * @param pageIndex
     */
    public void setPageIndex(Integer pageIndex) {
        if (pageIndex<1){
            this.pageIndex = 1;
        }else if (pageIndex>pageCount){
            this.pageIndex = pageCount;
        }else {
            this.pageIndex = pageIndex;
        }
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize<=0){
            this.pageSize =5;
        }else{
            this.pageSize = pageSize;
        }
        //计算页数
        setPageCount();
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        if (totalCount<=0){
            this.totalCount =0;
        }else{
            this.totalCount = totalCount;
        }
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount() {
        //由总数量/页面容量
        this.pageCount = totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getStartRow(){
        return (this.pageIndex-1)*this.getPageSize();
    }
}
