/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.services.wrappers;

/**
 *
 * @author shady
 */
public class SaveUserWrapper {
    
    
     private int active;
     private String fullName;
     private String password;
     private String userName;
     private int userType;

    public SaveUserWrapper(int active, String fullName, String password, String userName, int userType) {
        this.active = active;
        this.fullName = fullName;
        this.password = password;
        this.userName = userName;
        this.userType = userType;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

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

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
    
     
}
