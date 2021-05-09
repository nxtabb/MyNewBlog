package com.hrbeu.dao.admin;

import com.hrbeu.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    User queryUser(@Param("username") String username);
    int addUser(User user);
    User queryUserById(Long userId);
}
