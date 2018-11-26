/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.dicom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.apache.commons.io.FileUtils;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che3.data.Tag;

/**
 *
 * @author shady
 */
public class ReadDicomTags {

    static ArrayList<File> files = new ArrayList<>();

    int directoryNumber = 1;

    public ReadDicomTags() {

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("/home/shady/Desktop"));
        chooser.setDialogTitle("Select Directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): " + chooser.getSelectedFile().getAbsolutePath());
            //System.out.println("getSelectedFile() : " + chooser.getSelectedFile());

            //  /home/shady/Desktop/Dicom
            String Path = chooser.getSelectedFile().getAbsolutePath();

            List files = getFiles(Path);

            int x;
            int y = 0;
            for (x = 0; x < files.size(); x++) {

                //important to get dicom data
               // String dicomTags = ReadDicomTags.getDicomTags(files.get(y).toString());

              //  System.out.println("Tage Number "+ (x+1) + dicomTags);

                for (int i = 0; i < 5; i++) {

                    if (y < files.size()) {

                        String newDicomName = ReadDicomTags.getSOPInstanceUID(files.get(y).toString());

                       // System.out.println("newDicomName" + newDicomName);
                        
                       try {

                            FileUtils.copyFile((File) (files.get(y)), new File("/home/shady/Desktop/Dicom/Dicom_num" + directoryNumber + File.separator + newDicomName + ".dcm"));

                        } catch (IOException ex) {
                            Logger.getLogger(ReadDicomTags.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        y++;

                        // System.out.println("file is " );
                    } else {
                        break;
                    }
                }
                directoryNumber++;

            }

        } else {
            System.out.println("No Selection ");
        }
    }

    public static List getFiles(String directoryName) {

        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fList = directory.listFiles();

        for (File file : fList) {
            if (file.isFile()) {

                files.add(file);

            } else if (file.isDirectory()) {

                getFiles(file.getAbsolutePath());
            }
        }
        return files;
    }

    public static String getDicomTags(String DicomPath) {

        DicomObject dcmObj = null;
        DicomInputStream din = null;
        String data = null;
        try {

            din = new DicomInputStream(new File(DicomPath));

            dcmObj = din.readDicomObject();

            data = "{ \" Patient Name \" : " + dcmObj.getString(Tag.PatientName)
                    + ",  \" Patient Age : \" " + dcmObj.getString(Tag.PatientAge)
                    + ",  \" Patient Sex : \" " + dcmObj.getString(Tag.PatientSex)
                    + ",  \" StudyInstance UID : \" " + dcmObj.getString(Tag.StudyInstanceUID)
                    + ",  \" SeriesInstance UID : \" " + dcmObj.getString(Tag.SeriesInstanceUID)
                    + ",  \" SOPInstance UID : \" " + dcmObj.getString(Tag.SOPInstanceUID);

        } catch (IOException ex) {
            Logger.getLogger(ReadDicomTags.class.getName()).log(Level.SEVERE, null, ex);
        }

        return data;

    }

    public static String getSOPInstanceUID(String DicomPath) {

        DicomObject dcmObj = null;
        DicomInputStream din = null;
        String sopInstanceUID = null;
        try {

            din = new DicomInputStream(new File(DicomPath));

            dcmObj = din.readDicomObject();

            sopInstanceUID = dcmObj.getString(Tag.SOPInstanceUID);

        } catch (IOException ex) {
            Logger.getLogger(ReadDicomTags.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sopInstanceUID;

    }

    public static void main(String[] args) {

        new ReadDicomTags();

    }

}
