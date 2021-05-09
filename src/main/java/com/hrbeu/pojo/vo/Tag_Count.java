package com.hrbeu.pojo.vo;

import java.io.Serializable;

public class Tag_Count implements Serializable {
    private Long tagId;
    private Integer count;
    private String tagName;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Integer getCount() {
        return count;
    }

    public String getTagName() {
        return tagName;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Tag_Count(){}
    public Tag_Count(Long tagId,Integer count,String tagName){
        this.tagId = tagId;
        this.count = count;
        this.tagName=tagName;
    }
}
