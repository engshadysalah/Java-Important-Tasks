/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.services.wrappers;

/**
 *
 * @author mahmoud
 */
public class RadiologistVaildation {
    String userDeviceId;
    String userName;

    public RadiologistVaildation() {
    }

    public RadiologistVaildation(String userDeviceId, String userName) {
        this.userDeviceId = userDeviceId;
        this.userName = userName;
    }

    public String getUserDeviceId() {
        return userDeviceId;
    }

    public void setUserDeviceId(String userDeviceId) {
        this.userDeviceId = userDeviceId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    
}
