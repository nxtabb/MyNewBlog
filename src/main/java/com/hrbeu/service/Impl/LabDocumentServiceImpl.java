package com.hrbeu.service.Impl;

import com.hrbeu.dao.LabDocumentDao;
import com.hrbeu.pojo.Document;
import com.hrbeu.pojo.vo.Tag_Count;
import com.hrbeu.pojo.vo.Type_Count;
import com.hrbeu.service.LabDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabDocumentServiceImpl implements LabDocumentService {
    @Autowired
    private LabDocumentDao labDocumentDao;
    @Override
    public List<Document> getPublishedDocument(int pageIndex, int pageSize) {
        int documentIndex = pageSize*(pageIndex-1);
        return labDocumentDao.getPublishedDocument(documentIndex,pageSize);

    }

    @Override
    public int getPublishedDocumentCount() {
        return labDocumentDao.getPublishedDocumentCount();
    }

    @Override
    public List<Type_Count> getTypeAndCount(Integer typeCount) {
        return labDocumentDao.getTypeAndCount(typeCount);
    }


    @Override
    public List<Tag_Count> getTagAndCount(Integer tagCount) {
        return labDocumentDao.getTagAndCount(tagCount);
    }

    @Override
    public List<Document> getRecommendDocumentList(Integer recommendDocumentCount) {
        return labDocumentDao.getRecommendDocumentList(recommendDocumentCount);
    }

    @Override
    public List<Document> queryBySearch(String query, int pageIndex, int pageSize) {
        int documentIndex = pageSize*(pageIndex-1);
        return labDocumentDao.queryBySearch(query,documentIndex,pageSize);
    }

    @Override
    public int queryBySearchCount(String query) {
        return labDocumentDao.queryCountBySearch(query);
    }

    @Override
    public List<Type_Count> getTypeAndCountByTypeId() {
        return labDocumentDao.getTypeAndCountByTypeId();
    }

    @Override
    public List<Tag_Count> getTagAndCountByTagId() {
        return labDocumentDao.getTagAndCountByTagId();
    }


}
