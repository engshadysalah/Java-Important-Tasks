/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.work;

import javax.swing.JOptionPane;

/**
 *
 * @author root
 */
public class Display {

    private double size;
    private String type;
    private boolean power;
    int britn = 0;

    Display(String type) {
        this.type = type;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;

    }

    public int getBritn() {
        return britn;
    }

    public void setBritn(int britn) {
        this.britn = britn;
    }

    public void increaseBri() {
        if (britn < 100) {
            britn++;
        }

    }

    public void decreaseBri() {
        if (britn > 0) {
            britn--;
        }
    }

    public String getType() {
        return type;
    }

    public boolean isPower() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }

}
