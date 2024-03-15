package com.zte.pojo;

import java.util.List;

public class PageBean<T> {

    private int totalCount;//�ܼ�¼��
    private int totalPage;//��ҳ��
    private int currentPage;//��ǰҳ��
    private int pageSize;//ÿҳ��ʾ������

    private List<T> list;//ÿҳ��ʾ�����ݼ���

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}

