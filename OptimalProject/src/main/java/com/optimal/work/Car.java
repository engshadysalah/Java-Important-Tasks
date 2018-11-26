/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.work;

import java.awt.Color;

/**
 *
 * @author root
 */
public class Car {

    private String model;

    private Color color;

    private double cost;

    private int speed;
    private boolean power;

    private double oilQuantity;

    private int drivingLevel;

    private double movingDirectionValue;

    public Car(String model) {
        this.model = model;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean getPower() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }

    public void increaseSpeedMove() {

        if (power == true) {
            if (speed <= 500 && speed > 0) {
                speed++;
                if (speed >= 500) {
                    speed = 500;
                }
            }

        }
    }

    public void decreaseSpeedMove() {

        if (power == true) {
            if (speed > 0 && speed <= 500) {
                speed--;
                if (speed <= 0) {
                    speed = 0;
                }
            }

        }
    }

    public double getOilQuantity() {
        return oilQuantity;
    }

    public void setOilQuantity(double oilQuantity) {
        this.oilQuantity = oilQuantity;
    }

    public int getDrivingLevel() {
        return drivingLevel;
    }

    public void setDrivingLevel(int drivingLevel) {
        this.drivingLevel = drivingLevel;
    }

    public void increaseDrivingLevel() {

        if (power == true) {

            if (drivingLevel < 5 && drivingLevel > 0) {
                drivingLevel++;
                if (drivingLevel >= 5) {

                }
            }
        }
    }

    public void decreaseDrivingLevel() {

        if (power == true) {

            if (drivingLevel > 0 && drivingLevel < 5) {
                drivingLevel--;
                if (drivingLevel < 0) {

                }
            }
        }
    }

    public double getMovingDirection() {
        return movingDirectionValue;
    }

    public void setMovingDirection(double movingDirection) {
        this.movingDirectionValue = movingDirection;
    }

    public void leftDirection() {

        if (power == true) {

            if (movingDirectionValue < 0 && movingDirectionValue > -100) {

                movingDirectionValue -= 1;
            }

        }
    }

    public void rightDirection() {

        if (power == true) {

            if (movingDirectionValue > 0 && movingDirectionValue < 100) {

                movingDirectionValue += 1;
            }

        }
    }

    
    
    public static void main(String[] args) {
        Car fiat=new Car("Fiat 128");
        fiat.setColor(Color.RED);
        
        
        fiat.getColor();
        
        fiat.getCost();
        
        
        
        Car bmw=new Car("BMW");
        
     
    }
}
