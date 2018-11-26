/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.work;

/**
 *
 * @author root
 */
public class AirCon {

    private String type;

    private boolean power;

    private int fan;

    private int temperature;

    private boolean swing;

    public AirCon(String type) {
        this.type = type;
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

    public int getFan() {
        return fan;
    }

    public void setFan(int fan) {
        this.fan = fan;
    }

    public void increasFanLevel() {

        if (fan > -1) {
            fan++;
            if (fan > 5) {
                fan = 0;
            }
        }
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void increaetemp() {

        if (temperature >= 20 && temperature <= 28) {
            temperature++;
        }
    }

    public void decreastemp() {

        if (temperature <= 28 && temperature >= 20) {
            temperature--;
        }

    }

    public boolean isSwing() {
        return swing;
    }

    public void setSwing(boolean swing) {
        this.swing = swing;
    }

}
