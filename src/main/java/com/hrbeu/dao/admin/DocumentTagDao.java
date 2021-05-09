package com.hrbeu.dao.admin;

import com.hrbeu.pojo.Document;
import com.hrbeu.pojo.DocumentTag;
import com.hrbeu.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface DocumentTagDao {
    void saveDocumentsAndTags(@Param("document") Document document,@Param("tagList") List<Tag> tagList);
    int  deleteById(Long documentId);
    List<DocumentTag> queryTagListByDocumentId(Long documentId);
}
