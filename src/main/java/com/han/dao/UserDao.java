package com.han.dao;

import java.util.List;

import com.han.entity.User;

public interface UserDao {
    //根据主键删除
    int deleteByPrimaryKey(Integer id);

    //插入
    int insert(User model);

    //根据主键查询
    User selectByPrimaryKey(Integer id);

    //更新
    int update(User model);

    //查询全部
    List<User> selectAll();
}
