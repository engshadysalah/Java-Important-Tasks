/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.work;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author root
 */
public class Main {

    public static void main(String[] args) {

//        Engineers engineers = new Engineers();
//        engineers.setEmail("@");
//        System.out.println(engineers.getEmail());
//        Car bmw = new Car("BMW");
//        bmw.toString();
//
//  
//          Coin c = new Coin();
//           
//           
//          c.SetRandomCoinType();
//          
//          System.out.println(c.isCoinType());
//         
//
//          c.SetRandomCoinType();
//          
//          System.out.println(c.isCoinType());
//         
//
//          c.SetRandomCoinType();
//          
//          System.out.println(c.isCoinType());
//  
///============================================
        Stone s = new Stone();

        s.setRandomStoneLevel();

        System.out.println(s.getStoneLevel());

        Stone s1 = new Stone();

        s1.setRandomStoneLevel();

        System.out.println(s1.getStoneLevel());

        Stone s2 = new Stone();

        s2.setRandomStoneLevel();
        System.out.println(s2.getStoneLevel());
        
        
        Stone s3 = new Stone();

        s3.setRandomStoneLevel();
        System.out.println(s3.getStoneLevel());
        
        
        
        Stone s4 = new Stone();

        s4.setRandomStoneLevel();
        System.out.println(s4.getStoneLevel());
        
        
        
        Stone s5 = new Stone();

        s5.setRandomStoneLevel();
        System.out.println(s5.getStoneLevel());
        
//        Random random=new Random();
//        System.out.println(random.nextInt(6));
//         System.out.println(random.nextInt(6));
//          System.out.println(random.nextInt(6));
//           System.out.println(random.nextInt(6));
//           
//     


 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        System.out.println(currentDate);
    }
}
