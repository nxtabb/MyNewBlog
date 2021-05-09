package com.hrbeu.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Document implements Serializable {
    //文档id
    private Long documentId;
    //标题
    private String title;
    //内容
    private String content;
    //首图地址
    private String firstPicture;
    //标记(原创，翻译，转载)
    private String flag;
    //是否开启赞赏功能
    private Integer appreciate;
    //是否开启版权声明
    private Integer shareInfo;
    //是否可以评论
    private Integer commentAble;
    //是否发布
    private Integer published;
    //是否推荐
    private Integer recommend;

    private Date createTime;

    private Date lastEditTime;
    //type对象
    private Type type;
    //标签
    private List<Tag> tagList;
    //所属用户
    private User user;
    //描述
    private String description;




    public Document(){}

    public Document(Long documentId, String title, String content, String firstPicture, String flag, Integer appreciate, Integer shareInfo, Integer commentAble, Integer published, Integer recommend, Date createTime, Date lastEditTime, Type type, List<Tag> tagList, User user,String description) {
        this.documentId = documentId;
        this.title = title;
        this.content = content;
        this.firstPicture = firstPicture;
        this.flag = flag;
        this.appreciate = appreciate;
        this.shareInfo = shareInfo;
        this.commentAble = commentAble;
        this.published = published;
        this.recommend = recommend;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
        this.type = type;
        this.tagList = tagList;
        this.user = user;
        this.description = description;
    }

    public Document(String title, String content, String firstPicture, String flag, Integer viewCount, Integer appreciate, Integer shareInfo, Integer commentAble, Integer published, Integer recommend, Date createTime, Date lastEditTime, Type type, List<Tag> tagList, User user,String description) {
        this.title = title;
        this.content = content;
        this.firstPicture = firstPicture;
        this.flag = flag;
        this.appreciate = appreciate;
        this.shareInfo = shareInfo;
        this.commentAble = commentAble;
        this.published = published;
        this.recommend = recommend;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
        this.type = type;
        this.tagList = tagList;
        this.user = user;
        this.description = description;
    }

    public Document(String title, String content, String firstPicture, String flag, Integer appreciate, Integer shareInfo, Integer commentAble, Integer published, Integer recommend, Date lastEditTime, Type type, List<Tag> tagList, User user,String description) {
        this.title = title;
        this.content = content;
        this.firstPicture = firstPicture;
        this.flag = flag;
        this.appreciate = appreciate;
        this.shareInfo = shareInfo;
        this.commentAble = commentAble;
        this.published = published;
        this.recommend = recommend;
        this.lastEditTime = lastEditTime;
        this.type = type;
        this.tagList = tagList;
        this.user = user;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }


    public Integer getAppreciate() {
        return appreciate;
    }

    public void setAppreciate(Integer appreciate) {
        this.appreciate = appreciate;
    }

    public Integer getShareInfo() {
        return shareInfo;
    }

    public void setShareInfo(Integer shareInfo) {
        this.shareInfo = shareInfo;
    }

    public Integer getCommentAble() {
        return commentAble;
    }

    public void setCommentAble(Integer commentAble) {
        this.commentAble = commentAble;
    }

    public Integer getPublished() {
        return published;
    }

    public void setPublished(Integer published) {
        this.published = published;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
