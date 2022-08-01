package com.example.interiorshare.Adapter;

import java.io.Serializable;

public class ListViewItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private String titleStr;
    private String contentStr;
    private String detailStr;

    public String getTitle() { return titleStr; }
    public String getContent() {
        return contentStr;
    }
    public String getDetail() {
        return contentStr;
    }

    public void setTitle(String title) {
        titleStr = title;
    }
    public void setContent(String content) {
        contentStr = content;
    }
    public void setDetail(String detail) { detailStr = detail;}
}

