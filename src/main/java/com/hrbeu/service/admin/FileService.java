package com.hrbeu.service.admin;

import com.hrbeu.pojo.File;
import com.hrbeu.pojo.User;

import java.util.Date;
import java.util.List;

public interface FileService {
    int addFile(String fileName, String filePath, Long userId, Long documentId, String fileOriginName, Date createTime,Date lastEditTime);
    void deleteFile(Long documentId, User user);
    List<File> getFileListInfo(Long documentId, Long userId);
    List<File> getFileListInfo(Long documentId);
    void onlydeleteFile(Long fileId);
    File queryFile(Long fileId);
    void updateFile(Long fileId,String fileName, String filePath, Long userId, Long documentId, String fileOriginName, Date createTime, Date lastEditTime);
}
