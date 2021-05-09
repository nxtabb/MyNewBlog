package com.hrbeu.pojo;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Long userId;
    private String nickname;
    private String username;
    private String password;
    private String email;
    private String image;
    private Integer authority;
    private Date createTime;
    private Date lastEditTime;

    public User(){}

    public User(Long userId, String nickname, String username, String password, String email, String image, Integer authority, Date createTime, Date lastEditTime) {
        this.userId = userId;
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.image = image;
        this.authority = authority;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getAuthority() {
        return authority;
    }

    public void setAuthority(Integer authority) {
        this.authority = authority;
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
