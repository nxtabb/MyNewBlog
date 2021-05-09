package com.hrbeu.service.admin;

import com.hrbeu.pojo.Document;
import com.hrbeu.pojo.User;

import java.util.List;

public interface DocumentService {
    void saveDocument(Document document);
    Document queryDocument(Long documentId);
    List<Document> getDocumentList(Document document);
    void updateDocument(Document document);
    void deleteDocument(Long documentId);
    int documentCount(Document document);
    List<Document> getDocumentList(int pageIndex, int pageSize, Document document);
    Document queryDetailedDocument(Long documentId);
    Document getMostDetailDocument(Long documentId);
    //void createViewCount(Long documentId);
    List<Document> queryDocumentByTypeId(int pageIndex,int pageSize,Long typeId);
    int queryDocumentCountByTypeId(Long typeId);
    List<Document> queryDocumentByTagId(int pageIndex, int pageSize, Long tagId);
    int queryDocumentCountByTagId(Long tagId);
    List<String> getTimeGroupByYearAndMonth();
    List<Document> findDocumentByTime(String time);
    void deleteDocumentAndFileAndComment(Long documentId, User user);
}
