package com.hrbeu.service.admin.Impl;

import com.hrbeu.dao.CommentDao;
import com.hrbeu.dao.admin.DocumentDao;
import com.hrbeu.dao.admin.DocumentTagDao;
import com.hrbeu.dao.admin.FileDao;
import com.hrbeu.dao.admin.TagDao;
import com.hrbeu.pojo.*;
import com.hrbeu.service.admin.DocumentService;
import com.hrbeu.service.admin.FileService;
import com.hrbeu.utils.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentDao documentDao;
    @Autowired
    private DocumentTagDao documentTagDao;
    @Autowired
    private TagDao tagDao;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private FileService fileService;


    @Override
    public void saveDocument(Document document) {
        documentDao.saveDocument(document);
        document.setDocumentId(document.getDocumentId());
        List<Tag> tagList = document.getTagList();
        documentTagDao.saveDocumentsAndTags(document,tagList);

    }

    @Override
    public Document queryDocument(Long documentId) {
        return documentDao.queryDocument(documentId);
    }

    @Override
    public List<Document> getDocumentList(int pageIndex, int pageSize, Document document) {
        int documentIndex = pageSize*(pageIndex-1);
        return documentDao.getDocumentList(documentIndex,pageSize,document);
    }

    @Override
    public Document queryDetailedDocument(Long documentId) {
        return documentDao.queryDetailedDocument(documentId);
    }

    @Override
    public Document getMostDetailDocument(Long documentId) {
        return documentDao.getMostDetailDocument(documentId);
    }

//    @Override
//    public void createViewCount(Long documentId) {
//        documentDao.createViewCount(documentId);
//    }

    @Override
    public List<Document> queryDocumentByTypeId(int pageIndex,int pageSize,Long typeId) {
        int documentIndex = pageSize*(pageIndex-1);
        return documentDao.queryDocumentByTypeId(documentIndex,pageSize,typeId);
    }

    @Override
    public int queryDocumentCountByTypeId(Long typeId) {
        return documentDao.queryDocumentCountByTypeId(typeId);
    }

    @Override
    public List<Document> queryDocumentByTagId(int pageIndex, int pageSize, Long tagId) {
        int documentIndex = pageSize*(pageIndex-1);
        List<Document> documentList= documentDao.queryDocumentByTagId(documentIndex,pageSize,tagId);
        for(Document document:documentList){
            List<DocumentTag> documentTagList = documentTagDao.queryTagListByDocumentId(document.getDocumentId());
            List<Tag> tagList = new ArrayList<>();
            for(DocumentTag documentTag:documentTagList){
                Tag tag = tagDao.queryTag(documentTag.getTagId());
                tagList.add(tag);
            }
            document.setTagList(tagList);
        }
        return documentList;
    }

    @Override
    public int queryDocumentCountByTagId(Long tagId) {
        return documentDao.queryDocumentCountByTagId(tagId);
    }

    @Override
    public List<String> getTimeGroupByYearAndMonth() {
        return documentDao.getTimeGroupByYearAndMonth();
    }

    @Override
    public List<Document> findDocumentByTime(String time) {
        return documentDao.findDocumentByTime(time);
    }

    @Override
    @Transactional
    public void deleteDocumentAndFileAndComment(Long documentId, User user) {
        commentDao.deleteCommentByDocumentId(documentId);
        deleteFile(documentId,user);
        documentTagDao.deleteById(documentId);
        documentDao.deleteDocument(documentId);


    }

    @Override
    public List<Document> getDocumentList(Document document) {

        return documentDao.getList(document);
    }


    @Override
    public void updateDocument(Document document) {
        documentDao.updateDocument(document.getDocumentId(),document);
        document = documentDao.queryDetailedDocument(document.getDocumentId());
        documentTagDao.deleteById(document.getDocumentId());
        documentTagDao.saveDocumentsAndTags(document,document.getTagList());
    }

    @Override
    public void deleteDocument(Long documentId) {
        documentTagDao.deleteById(documentId);
        documentDao.deleteDocument(documentId);
    }
    @Override
    public int documentCount(Document document) {
        return documentDao.documentCount(document);
    }

    public void deleteFile(Long documentId, User user) {
        //删除文件
        Document document = documentDao.queryDocument(documentId);
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
}
