package com.hrbeu.service.Impl;

import com.hrbeu.dao.CommentDao;
import com.hrbeu.pojo.Comment;
import com.hrbeu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private static List<Comment> tempReplys = new ArrayList<>();
    @Autowired
    private CommentDao commentDao;

    @Transactional
    @Override
    public int saveComment(Comment comment) {
        Long commentParentId = comment.getParentId();
        if(commentParentId!=-1){
            comment.setParentId(commentParentId);
            comment.setCreateTime(new Date());
        }
        else {
            comment.setParentId(null);
            comment.setCreateTime(new Date());
        }
        int effNum = commentDao.saveComment(comment);
        return effNum;
    }

    @Override
    public List<Comment> queryByDocumentId(Long documentId) {
        return commentDao.queryByDocumentId(documentId);
    }
    @Override
    public Comment getParentComment(Comment comment) {
        Long parentId = comment.getParentId();
        if(parentId!=null){
            return commentDao.queryParentComment(parentId);
        }else {
            return null;
        }
    }

    @Override
    public void deleteComment(Long commentId) {
        commentDao.deleteComment(commentId);
    }

    @Override
    public Long queryDocumentOfComment(Long commentId) {
        return commentDao.queryDocumentOfComment(commentId);
    }

    //评论功能查询service层函数
    @Override
    public List<Comment> listCommentByDocumentId(Long documentId) {
        List<Comment> rootCommentList = commentDao.queryRootCommentByDocumentId(documentId);
        for(Comment comment:rootCommentList){
            comment.setCommnets(commentDao.queryChildComment(comment));
        }
        combineChildren(rootCommentList);
        return rootCommentList;
    }
    //方法1
    private void combineChildren(List<Comment> comments) {
        for(Comment comment:comments){
            //得到节点的一个子集
            List<Comment> replys1 =comment.getCommnets();
            for(Comment reply1:replys1){
                List<Comment> child = commentDao.queryChildComment(reply1);
                reply1.setCommnets(child);
                recursive(reply1);
            }
            comment.setCommnets(tempReplys);
            tempReplys = new ArrayList<>();
        }
    }
    //方法2
    private void recursive(Comment comment){
        tempReplys.add(comment);
        if(comment.getCommnets()!=null&&comment.getCommnets().size()>0){
            List<Comment> replys = comment.getCommnets();
            for(Comment reply:replys){
                List<Comment> child = commentDao.queryChildComment(reply);
                reply.setCommnets(child);
                recursive(reply);
            }
        }
    }
}
