/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.teachermodel;

import java.util.Date;
import java.util.List;

/**
 *
 * @author root
 */
public class TeacherModel {

    private String teacherName;
    private String gender;
     private Date teacherBirthDate;
    private String countery;
    private String city;
    private String street;
    private String teacherHabbits;
    private int[] selectedhabitsIndex;

 
    public String getCountery() {
        return countery;
    }

    public void setCountery(String countery) {
        this.countery = countery;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int[] getSelectedhabitsIndex() {
        return selectedhabitsIndex;
    }

    public void setSelectedhabitsIndex(int[] selectedhabitsIndex) {
        this.selectedhabitsIndex = selectedhabitsIndex;
    }
    private String imagePath;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getTeacherBirthDate() {
        return teacherBirthDate;
    }

    public void setTeacherBirthDate(Date teacherBirthDate) {
        this.teacherBirthDate = teacherBirthDate;
    }

    public String getTeacherHabbits() {
        return teacherHabbits;
    }

    public void setTeacherHabbits(String teacherHabbits) {
        this.teacherHabbits = teacherHabbits;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
