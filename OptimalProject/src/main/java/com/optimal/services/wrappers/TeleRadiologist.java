/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.services.wrappers;


import com.optimal.util.FileUtil;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mohamed Shehata
 */

public class TeleRadiologist {

    private Integer id;
    private String name;
    private String email;
    private String address;
    private String mobile;
    private String userDeviceUID;
    private int active;

    private String ftpUsername;
    private String ftpPassword;
    private Integer ftpPort;
    private String ftpIp;
    private String messagingServerIp;

    private List<RadiologistSpecialty> specialties = new ArrayList<>(0);

    public TeleRadiologist() {
        userDeviceUID = FileUtil.getStationUID() + "@" + FileUtil.getPublicIP();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /* public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }*/
    public String getUserDeviceUID() {
        return userDeviceUID;
    }

    public void setUserDeviceUID(String userDeviceUID) {
        this.userDeviceUID = userDeviceUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getFtpUsername() {
        return ftpUsername;
    }

    public void setFtpUsername(String ftpUsername) {
        this.ftpUsername = ftpUsername;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public Integer getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(Integer ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpIp() {
        return ftpIp;
    }

    public void setFtpIp(String ftpIp) {
        this.ftpIp = ftpIp;
    }

    public String getMessagingServerIp() {
        return messagingServerIp;
    }

    public void setMessagingServerIp(String messagingServerIp) {
        this.messagingServerIp = messagingServerIp;
    }

    public List<RadiologistSpecialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<RadiologistSpecialty> specialties) {
        this.specialties = specialties;
    }

}
