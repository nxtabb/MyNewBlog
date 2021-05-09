package com.hrbeu.dao;

import com.hrbeu.pojo.Document;
import com.hrbeu.pojo.vo.Tag_Count;
import com.hrbeu.pojo.vo.Type_Count;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface LabDocumentDao {
    List<Document> getPublishedDocument(@Param("documentIndex") Integer documentIndex,@Param("pageSize")Integer pageSize);
    int getPublishedDocumentCount();
    List<Type_Count> getTypeAndCount(Integer typeCount);
    List<Tag_Count> getTagAndCount(Integer tagCount);
    List<Document> getRecommendDocumentList(Integer recommendDocumentCount);
    List<Document> queryBySearch(@Param("query") String query,@Param("documentIndex") Integer documentIndex,@Param("pageSize") Integer pageSize);
    int queryCountBySearch(@Param("query") String query);
    List<Type_Count> getTypeAndCountByTypeId();
    List<Tag_Count> getTagAndCountByTagId();
}
