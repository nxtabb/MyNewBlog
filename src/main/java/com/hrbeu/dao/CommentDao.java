package com.hrbeu.dao;

import com.hrbeu.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface CommentDao {
    List<Comment> queryByDocumentId(Long documentId);
    int saveComment(Comment comment);
    List<Comment> queryRootCommentByDocumentId(Long documentId);
    List<Comment> queryChildComment(Comment comment);
    Comment queryParentComment(Long parentId);
    void deleteComment(Long commentId);
    Long queryDocumentOfComment(Long commentId);
    void deleteCommentByDocumentId(Long documentId);
}
