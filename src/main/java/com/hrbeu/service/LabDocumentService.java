package com.hrbeu.service;

import com.hrbeu.pojo.Document;
import com.hrbeu.pojo.vo.Tag_Count;
import com.hrbeu.pojo.vo.Type_Count;

import java.util.List;

public interface LabDocumentService {
    List<Document> getPublishedDocument(int pageIndex, int pageSize);
    int getPublishedDocumentCount();
    List<Type_Count> getTypeAndCount(Integer typeCount);
    List<Tag_Count> getTagAndCount(Integer tagCount);
    List<Document> getRecommendDocumentList(Integer recommendDocumentCount);
    List<Document> queryBySearch(String query,int pageIndex,int pageSize);
    int queryBySearchCount(String query);
    List<Type_Count> getTypeAndCountByTypeId();
    List<Tag_Count> getTagAndCountByTagId();
}
