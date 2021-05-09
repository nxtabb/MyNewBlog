package com.hrbeu.service.admin;

import com.hrbeu.pojo.Type;

import java.util.List;

public interface TypeService {
    int saveType(Type type);
    Type queryType(Long typeId);
    List<Type> queryTypeList(int pageIndex,int pageSize);
    int updateType(Long typeId,Type type);
    void deleteType(Long typeId);
    int typeCount();
    int checkType(String typeName);
    List<Type> queryAllType();
}
