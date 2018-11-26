/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.file;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Collections;

/**
 *
 * @author root
 */
public class RandomData {

    public static void main(String[] args) {

        writeNoDuplicate("/home/shady/(1)Optimal/(3)Work/OldNetBeansProjects/Basics File/random");
        
       // RemoveData.readFile("/home/shady/(1)Optimal/(3)Work/OldNetBeansProjects/Basics File/randomSorted");
 
    }

    public static void write(String filePath) {

        int[] sortedNumbers = new int[100];

        FileWriter f0;
        FileWriter sorted;
        try {

            String newLine = System.getProperty("line.separator");

            f0 = new FileWriter(filePath);
            sorted = new FileWriter(filePath + "Sorted");
            Random random = new Random();

            int randomNumber;
            for (int i = 0; i < sortedNumbers.length; i++) {
                randomNumber = random.nextInt(100);
                f0.write(randomNumber + newLine);
                sortedNumbers[i] = randomNumber;
            }

            Arrays.sort(sortedNumbers);

            for (int i = 0; i < sortedNumbers.length; i++) {
                System.out.println(sortedNumbers[i]);
                sorted.write(sortedNumbers[i] + newLine);

            }

            f0.close();
            sorted.close();
        } catch (IOException ex) {
            Logger.getLogger(RemoveData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void writeNoDuplicate(String filePath) {

        int[] sortedNumbers = new int[100];

        FileWriter f0;
        FileWriter sorted;
        try {

            String newLine = System.getProperty("line.separator");

            f0 = new FileWriter(filePath);
            sorted = new FileWriter(filePath + "Sorted");

            Integer[] arr = new Integer[100];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = i;
            }
            Collections.shuffle(Arrays.asList(arr));

            int randomNumber;
            for (int i = 0; i < sortedNumbers.length; i++) {
                randomNumber = arr[i];
                System.out.println(arr[i]);
                f0.write(randomNumber + newLine);
                sortedNumbers[i] = randomNumber;
            }

            Arrays.sort(sortedNumbers);

            for (int i = 0; i < sortedNumbers.length; i++) {
                // System.out.println(sortedNumbers[i]);
                sorted.write(sortedNumbers[i] + newLine);

            }

            f0.close();
            sorted.close();
        } catch (IOException ex) {
            Logger.getLogger(RemoveData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
