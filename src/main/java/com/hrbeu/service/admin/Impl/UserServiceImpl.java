package com.hrbeu.service.admin.Impl;

import com.hrbeu.dao.admin.UserDao;
import com.hrbeu.pojo.User;
import com.hrbeu.service.admin.UserService;
import com.hrbeu.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        User user = userDao.queryUser(username);
        if(user==null){
            return null;
        }else {
            if(user.getPassword().equals(MD5Util.md5(password))){
                return user;
            }else {
                return null;
            }
        }
    }

    @Override
    public int userExists(String username) {
        User user = userDao.queryUser(username);
        if(user!=null){
            return 1;
        }
        else {
            return 0;
        }
    }

    @Override
    public int addUser(User user) {
        user.setAuthority(1);
        user.setCreateTime(new Date());
        user.setLastEditTime(new Date());
        user.setImage("/images/user.png");
        return userDao.addUser(user);
    }

    @Override
    public User queryUserById(Long userId) {
        return userDao.queryUserById(userId);
    }
}
