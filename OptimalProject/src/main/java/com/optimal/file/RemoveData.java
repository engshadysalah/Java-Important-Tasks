/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.file;

import com.google.common.io.Files;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class RemoveData {

    public static void main(String[] args) {

        write("/home/shady/(1)Optimal/(3)Work/OldNetBeansProjects/Basics File/TestFile");
       // removeMultiples("/home/shady/(1)Optimal/(3)Work/OldNetBeansProjects/Basics File/TestFile");

        //  readFile("/home/shady/(1)Optimal/(3)Work/OldNetBeansProjects/Basics File/TestFile");
          // readFile("/home/shady/(1)Optimal/(3)Work/OldNetBeansProjects/Basics File/TestFileNew");

        
    }

    public static void write(String filePath) {

        FileWriter f0;
        try {

            String newLine = System.getProperty("line.separator");

            f0 = new FileWriter(filePath);

            for (int i = 0; i <= 100; i++) {
                f0.write(i + newLine);
            }

            f0.close();

        } catch (IOException ex) {
            Logger.getLogger(RemoveData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void removeMultiples(String filePath) {

        File mainFile = new File(filePath);
        File multipleFile = new File(mainFile.getParent() + "/Multiples");
        File nweMainFile = new File(mainFile.getParent() + "/ " + mainFile.getName());

        try {
            BufferedReader reader = new BufferedReader(new FileReader(mainFile));

            BufferedWriter multipwriter = new BufferedWriter(new FileWriter(multipleFile));

            BufferedWriter newWriter = new BufferedWriter(new FileWriter(nweMainFile));

            String currentLine = null;

            while ((currentLine = reader.readLine()) != null) {

                if ((Integer.parseInt(currentLine)) % 5 == 0) {

                    multipwriter.write(currentLine + System.getProperty("line.separator"));
                    System.out.println("yes " + currentLine);

                } else {

                    newWriter.write(currentLine + System.getProperty("line.separator"));
                    System.out.println("no " + currentLine);
                }

            };

            multipwriter.close();
            newWriter.close();
            reader.close();

            mainFile.delete();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void readFile(String filePath) {

        File file = new File(filePath);

        try {
            FileInputStream fis = new FileInputStream(file);

            //Construct BufferedReader from InputStreamReader
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            br.close();
        } catch (IOException ex) {
            Logger.getLogger(Files.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
