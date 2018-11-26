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
public class UserWrapper {

    private Integer userId;
    private String userName;
    private String newPassword;
    private String oldPassword;


    public UserWrapper(Integer userId, String userName, String newPassword, String oldPassword) {
        this.userId = userId;
        this.userName = userName;
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
     }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

 
    
    
}
