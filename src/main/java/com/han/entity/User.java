package com.han.entity;

import java.io.Serializable;

public class User implements Serializable{
    private static final long serialVersionUID = -8634976958399246735L;

    private int id;
    private String userName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", userName=" + userName + "]";
    }
}
