package com.hrbeu.service.admin.Impl;

import com.hrbeu.dao.admin.FileDao;
import com.hrbeu.pojo.Document;
import com.hrbeu.pojo.File;
import com.hrbeu.pojo.User;
import com.hrbeu.service.admin.DocumentService;
import com.hrbeu.service.admin.FileService;
import com.hrbeu.utils.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileDao fileDao;
    @Autowired
    private DocumentService documentService;


    @Override
    public int addFile(String fileName, String filePath, Long userId, Long documentId, String fileOriginName, Date createTime, Date lastEditTime) {
        return fileDao.addFile(fileName,filePath,userId,documentId,fileOriginName,createTime,lastEditTime);
    }

    @Override
    public void deleteFile(Long documentId, User user) {
        //删除文件
        Document document = documentService.queryDocument(documentId);
        String documentTitle = document.getTitle();
        String filePathStr = PathUtil.getBasePath()+ java.io.File.separator+user.getUsername()+ java.io.File.separator+documentTitle;
        java.io.File filePath = new java.io.File(filePathStr);
        //判断是否存在
        if(filePath.exists()){
            //判断是否是文件夹
            if(filePath.isDirectory()){
                //获取文件夹下的所有文件
                java.io.File[] files = filePath.listFiles();
                //遍历删除
                for(java.io.File f:files){
                    f.delete();
                }
            }
            //最后删除该文件夹或文件。
            filePath.delete();
        List<File> fileList = fileDao.queryFileList(documentId,user.getUserId());
        for(File file:fileList){
                //删除表数据
                fileDao.deleteFile(file.getFileId());
            }
        }


    }
    //查询update页面时使用
    @Override
    public List<File> getFileListInfo(Long documentId, Long userId) {
        return fileDao.queryFileList(documentId,userId);
    }
    //  查询首页文件时使用
    @Override
    public List<File> getFileListInfo(Long documentId) {
        return fileDao.queryFileListByDocumentId(documentId);
    }

    @Override
    public void onlydeleteFile(Long fileId) {
        //删除文件
        File file = fileDao.queryFileById(fileId);
        String filePathStr = PathUtil.getBasePath()+file.getFilePath();
        java.io.File filePath = new java.io.File(filePathStr);
        //判断是否存在
        if(filePath.exists()){
            //判断是否是文件夹
            if(filePath.isDirectory()){
                //获取文件夹下的所有文件
                java.io.File[] files = filePath.listFiles();
                //遍历删除
                for(java.io.File f:files){
                    f.delete();
                }
            }
            //最后删除该文件夹或文件。
            filePath.delete();
        }
        //删除表数据
        fileDao.deleteFile(file.getFileId());
    }

    @Override
    public File queryFile(Long fileId) {
        return fileDao.queryFileById(fileId);
    }

    @Override
    @Transactional
    public void updateFile(Long fileId,String fileName, String filePath, Long userId, Long documentId, String fileOriginName, Date createTime, Date lastEditTime) {
        File file = fileDao.queryFileById(fileId);
        String filePathStr = PathUtil.getBasePath()+file.getFilePath();
        java.io.File fileFordel = new java.io.File(filePathStr);
        //判断是否存在
        if(fileFordel.exists()){
            //判断是否是文件夹
            if(fileFordel.isDirectory()){
                //获取文件夹下的所有文件
                java.io.File[] files = fileFordel.listFiles();
                //遍历删除
                for(java.io.File f:files){
                    f.delete();
                }
            }
            //最后删除该文件夹或文件。
            fileFordel.delete();
        }
        fileDao.updateFile(fileId,fileName,filePath,fileOriginName,lastEditTime);
    }
}
