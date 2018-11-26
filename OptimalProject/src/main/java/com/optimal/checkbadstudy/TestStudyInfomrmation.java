/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.checkbadstudy;

/**
 *
 * @author shady
 */
public class TestStudyInfomrmation {

    public static void main(String[] args) {

        String dicomPath = "/home/shady/Desktop/1.3.12.2.1107.5.2.5.11090.5.0.5825045721782568.dcm";

        new CheckStudyInformation().checkStudyInformation(dicomPath);

        
    }
}
