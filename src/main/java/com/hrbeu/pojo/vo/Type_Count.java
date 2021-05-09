package com.hrbeu.pojo.vo;

import java.io.Serializable;

public class Type_Count implements Serializable {
    private Long typeId;
    private Integer count;
    private String typeName;


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getCount() {
        return count;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
    public Type_Count(){}
    public Type_Count(Long typeId,Integer count,String typeName){
        this.typeId = typeId;
        this.count = count;
        this.typeName = typeName;
    }
}

