package com.hrbeu.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Comment implements Serializable {
    private Long commentId;
    private String nickname;
    private String email;
    private String content;
    private Date createTime;
    //父评论
    private Comment parentComment;
    private Long documentId;
    private Long parentId;
    //子评论
    private List<Comment> commnets;
    private Integer adminComment;
    public Comment(){}

    public Comment(Long commentId, String nickname, String email, String content, Date createTime, Long documentId, Long parentId) {
        this.commentId = commentId;
        this.nickname = nickname;
        this.email = email;
        this.content = content;
        this.createTime = createTime;
        this.documentId = documentId;
        this.parentId = parentId;
    }

    public Integer getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(Integer adminComment) {
        this.adminComment = adminComment;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<Comment> getCommnets() {
        return commnets;
    }

    public void setCommnets(List<Comment> commnets) {
        this.commnets = commnets;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }
}
