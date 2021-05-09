package com.hrbeu.service.admin.Impl;

import com.hrbeu.dao.admin.TypeDao;
import com.hrbeu.pojo.Type;
import com.hrbeu.service.admin.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeDao typeDao;
    @Override
    public int saveType(Type type) {
        return typeDao.saveType(type);
    }

    @Override
    public Type queryType(Long typeId) {
        return typeDao.queryType(typeId);
    }

    @Override
    public List<Type> queryTypeList(int pageIndex, int pageSize) {
        int typeIndex = pageSize*(pageIndex-1);
        List<Type> typeList = typeDao.queryTypeList(typeIndex,pageSize);
        return typeList;
    }

    @Override
    public int updateType(Long typeId, Type type) {
        int effNum = typeDao.updateType(typeId,type);
        return effNum;
    }

    @Override
    public void deleteType(Long typeId) {
        typeDao.deleteType(typeId);
    }

    @Override
    public int typeCount() {
        return typeDao.typeCount();
    }

    @Override
    public int checkType(String typeName) {
        return typeDao.checkType(typeName);
    }

    @Override
    public List<Type> queryAllType() {
        return typeDao.queryAllType();
    }
}
