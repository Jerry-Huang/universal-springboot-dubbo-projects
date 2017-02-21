package com.universal.gw.domain;

import java.io.Serializable;

/**
 * Created by Jerryh on 2017/1/11.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 6386101278627960986L;
    
    private String userName;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
