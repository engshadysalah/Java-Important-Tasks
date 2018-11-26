/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.dicom;

import ij.IJ;
import ij.plugin.DICOM;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;

/**
 *
 * @author shady
 */
public class ShowDicom {

    public static void main(String[] args) {

        // displayDicomImageJ("/home/shady/Desktop/series-000001/image-000001.dcm");
        ShowDicom.check("/home/shady/Desktop/series-000001/image-000001.dcm");

    }

    public static void displayDicomImageJ(String uri) {

        InputStream reader = null;
        try {
            reader = new FileInputStream(uri);
            DICOM dcm = new DICOM(reader);
            dcm.run("Name");
            dcm.show();
            if (dcm.getWidth() == 0) {
                IJ.log("Error opening image.dicom");
            } else {
                dcm.show();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ShowDicom.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(ShowDicom.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    //http://forums.dcm4che.org/jiveforums/thread.jspa?threadID=2611
    Reader reader;

    public static void dispalyDicomImageDCM(String uri) {

        DicomObject dcmObj;
        DicomInputStream din = null;

        try {

            din = new DicomInputStream(new File(uri));

           
            dcmObj = din.readDicomObject();

           
            // din = new DicomInputStream(new BufferedInputStream(new FileInputStream(uri)));
            //dcmObj = din.readDicomObject();
////         ImageReader reader = new DicomImageReaderSpi().createReaderInstance();
//         FileImageInputStream input = new FileImageInputStream(new File(uri));
//         reader.setInput(input);
//            
//                 BufferedImage img = reader.read(i);
//            images.add(img);
//            
        } catch (IOException ex) {
            Logger.getLogger(ShowDicom.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void check(String s) {

        String[] array = s.split("/");
        String k = array[0];
        k += "/";
        k += array[1];
        k += "/";
        k += array[2];
        k += "/";
        k += array[3];
        k += "/";
        k += array[4];
        k += "/";
        
        System.out.println(k);

        System.out.println(s.length());

        if (s.length() == 0) {
            System.out.println("The path and filename are empty!");
            System.exit(0);
        }

        File source = new File(k, array[5]);

        Image image = null  ;
        try {
            image = ImageIO.read(source);
            //final BufferedImage image = ImageIO.read(new File(k, array[5]));

            if (image == null) {
                System.out.println("The image is empty or can't be read!");
                System.exit(0);
            }
        } catch (IOException ex) {
            Logger.getLogger(ShowDicom.class.getName()).log(Level.SEVERE, null, ex);
        }

        JFrame frame = new JFrame();
        final Rectangle bounds = new Rectangle(0, 0, 240, 240);

        JPanel panel = new JPanel();
        
        

        JLabel b = new JLabel(new ImageIcon(image));
        panel.add(b);
        frame.getContentPane().add(panel);
        panel.setPreferredSize(new Dimension(300, 300));
        frame.pack();
        frame.setVisible(true);
    }
    
    
  
}
