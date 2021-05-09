package com.hrbeu.service;

import com.hrbeu.pojo.Comment;

import java.util.List;

public interface CommentService {
    int saveComment(Comment comment);
    List<Comment> queryByDocumentId(Long documentId);
    List<Comment> listCommentByDocumentId(Long documentId);
    Comment getParentComment(Comment comment);
    void deleteComment(Long commentId);
    Long queryDocumentOfComment(Long commentId);
}
