package com.han.service;

import com.han.entity.User;

public interface UserService {
    //根据主键删除
    User deleteByPrimaryKey(Integer id);

    //插入
    User insert(User model);

    //根据主键查询
    User selectByPrimaryKey(Integer id);

    //更新
    User update(User model);
}
