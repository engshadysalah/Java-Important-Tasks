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
public class Coin {

    private boolean coinType;

    public void SetRandomCoinType() {

        Random random = new Random();
        coinType = random.nextBoolean();
        //  coinType = Math.random() < 0.5;
      
    }

    public boolean isCoinType() {
        return coinType;
    }
}
