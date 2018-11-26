/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.createdicomserver;

import com.google.common.eventbus.EventBus;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shady
 */
public class Test {

    public static void main(String[] args) {

    EventBus  eventBus  =new EventBus();
   

    DicomServer.init("192.168.1.7", 11116, "AETILTE", "/home/shady/Desktop/DICOM-SOTRE",eventBus);

            //  storescu -c WHATEVER@localhost:104 "C:\dcmsamples\MARTIN^ZANE^"\*
        
        
       /*
        try {
            
            DicomReader dicomReade = new DicomReader(new File("/home/shady/Desktop/1.3.12.2.1107.5.2.5.11090.5.0.5825045721782568.dcm"));
            System.out.println(dicomReade);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
      */
    }
}
