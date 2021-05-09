package com.hrbeu.pojo;

import java.io.Serializable;
import java.util.Date;

public class File implements Serializable {
    private Long fileId;
    private String fileName;
    private String filePath;
    private User user;
    private Document document;
    private String fileOriginName;
    private Date createTime;
    private Date lastEditTime;
    public File(){}

    public File(Long fileId, String fileName, String filePath, User user, Document document, String fileOriginName, Date createTime, Date lastEditTime) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.user = user;
        this.document = document;
        this.fileOriginName = fileOriginName;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getFileOriginName() {
        return fileOriginName;
    }

    public void setFileOriginName(String fileOriginName) {
        this.fileOriginName = fileOriginName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
}
