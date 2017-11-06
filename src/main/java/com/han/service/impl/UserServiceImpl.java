package com.han.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.han.annotation.RedisCache;
import com.han.annotation.RedisEvict;
import com.han.dao.UserDao;
import com.han.entity.User;
import com.han.service.UserService;

/**
 * @author mingshan
 *
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @RedisEvict(type = User.class)
    public User deleteByPrimaryKey(Integer id) {
        userDao.deleteByPrimaryKey(id);
        User user = new User();
        user.setId(id);
        return user;
    }

    @Override
    @RedisCache(type = User.class)
    public User insert(User user) {
        int id = userDao.insert(user);
        user.setId(id);
        return user;
    }

    @Override
    @RedisCache(type = User.class)
    public User selectByPrimaryKey(Integer id) {
        return userDao.selectByPrimaryKey(id);
    }

    @Override
    @RedisCache(type = User.class)
    public User update(User user) {
        userDao.update(user);
        return user;
    }

}
