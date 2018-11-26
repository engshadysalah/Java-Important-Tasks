/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.work;

import java.util.Random;

/**
 *
 * @author root
 */
public class Stone {

    private int stoneLevel;

    public int getStoneLevel() {
        return stoneLevel;
    }

    public void setRandomStoneLevel() {

//        double randomValue = (Math.random() * 6) + 1;
//        stoneLevel = (int) randomValue;

        Random random=new Random();
        stoneLevel=random.nextInt(6);
        
        if(stoneLevel==0){
           setRandomStoneLevel();
        }


    }

}
