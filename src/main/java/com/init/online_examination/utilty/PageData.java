package com.init.online_examination.utilty;


import org.springframework.data.domain.Page;

import java.io.Serializable;

public class PageData implements Serializable {
    private Object data;
    private Integer page;
    private Integer pageSize;
    private Long dataCount;
    private Integer pageCount;

    public PageData(Object data, Integer page, Integer pageSize, Long dataCount) {
        this.data = data;
        this.page = page;
        this.pageSize = pageSize;
        this.dataCount = dataCount;
        if (pageSize != null && !pageSize.equals(0)) {
            Double t = Math.ceil(dataCount / pageSize);
            this.pageCount = t.intValue();
        } else {
            this.pageCount = 0;
        }
    }

    public PageData(Page domainPage, Integer page, Integer pageSize) {
        this.data = domainPage.getContent();
        this.pageCount = domainPage.getTotalPages();
        this.dataCount = domainPage.getTotalElements();
        this.page = page;
        this.pageSize = pageSize;
    }

    public PageData(Page domainPage) {
        this.data = domainPage.getContent();
        this.pageCount = domainPage.getTotalPages();
        this.dataCount = domainPage.getTotalElements();
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getDataCount() {
        return dataCount;
    }

    public void setDataCount(Long dataCount) {
        this.dataCount = dataCount;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }
}
