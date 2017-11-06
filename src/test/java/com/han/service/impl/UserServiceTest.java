package com.han.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.han.entity.User;
import com.han.service.UserService;

@ContextConfiguration(locations="classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest extends AbstractJUnit4SpringContextTests {

    @Autowired 
    private UserService userService;

    @Test
    public void insertUserTest() {
        User user = new User();
        user.setUserName("eee");
        userService.insert(user);
    }

    @Test
    public void getUser() {
        User user = userService.selectByPrimaryKey(10);
        System.out.println(user);
    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setId(10);
        user.setUserName("3333");
        userService.update(user);
    }

    @Test
    public void deleteUser() {
        userService.deleteByPrimaryKey(10);
    }
}
