/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.dicom;

import com.optimal.util.DicomUtil;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dcm4che2.data.BasicDicomObject;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.io.DicomOutputStream;
import org.dcm4che3.data.Tag;

/**
 *
 * @author root
 */
public class EditDicomTags {

    // not worked
    public String PutDicomTags(String dicomPath) {

        DicomObject dcmObj = null;
        DicomInputStream din = null;
        String data = null;
        try {

            String[] array = dicomPath.split("/");
            String dicomName = null;
            String destinationFolder = null;

            for (int i = 0; i < array.length; i++) {
                destinationFolder = array[array.length - 2];
                dicomName = array[array.length - 1];
            }

            din = new DicomInputStream(new File(dicomPath));

            dcmObj = din.readDicomObject();

            dcmObj.putString(Tag.PatientName, dcmObj.vrOf(Tag.PatientName), "Shady Salah");
            dcmObj.putString(Tag.PatientID, dcmObj.vrOf(Tag.PatientID), "01284991399");
            dcmObj.putString(Tag.PatientAge, dcmObj.vrOf(Tag.PatientAge), "23");
            dcmObj.putString(Tag.PatientSex, dcmObj.vrOf(Tag.PatientSex), "Male");
            dcmObj.putString(Tag.StudyInstanceUID, dcmObj.vrOf(Tag.StudyInstanceUID), "1.2.3.4.5.6.7.8.9.10");
            dcmObj.putString(Tag.SOPInstanceUID, dcmObj.vrOf(Tag.SOPInstanceUID), " 20.30.40.50.60.70");
            dcmObj.putString(Tag.SOPInstanceUID, dcmObj.vrOf(Tag.SOPInstanceUID), " 12.13.14.15.16");
//
//            data = "{ \" Patient Name \" : " + dcmObj.getString(Tag.PatientName)
//                    + ",  \" Patient Age : \" " + dcmObj.getString(Tag.PatientAge)
//                    + ",  \" Patient Sex : \" " + dcmObj.getString(Tag.PatientSex)
//                    + ",  \" StudyInstance UID : \" " + dcmObj.getString(Tag.StudyInstanceUID)
//                    + ",  \" SeriesInstance UID : \" " + dcmObj.getString(Tag.SeriesInstanceUID)
//                    + ",  \" SOPInstance UID : \" " + dcmObj.getString(Tag.SOPInstanceUID);

            System.out.println("destinationFolder : " + destinationFolder + "  dicomName : " + dicomName);
            this.writeFile(dcmObj, destinationFolder, "\\" + dicomName);

        } catch (IOException ex) {
            Logger.getLogger(ReadDicomTags.class.getName()).log(Level.SEVERE, null, ex);
        }

        return data;

    }

    // https://awaiswaheed.wordpress.com/2012/07/02/dicom-tag-editing-using-dcm4che/
    
    public static void UpdateTags(String sourceFolder) {

        //now get all files in tag folder
        File[] allFiles = new File(sourceFolder).listFiles();

        DicomObject dcmObj = new BasicDicomObject();
        DicomInputStream din = null;
        for (int i = 0; i < allFiles.length; i++) {
            System.out.println("Current Image in Progress = " + (i + 1)
                    + " out of = " + allFiles.length);
            try {
                din = new DicomInputStream(allFiles[i]);
                din.readDicomObject(dcmObj, -1);
                //System.out.println(" Tag.PatientID  *******************   " + Tag.PatientID);

                //dcmObj.putString(1048608, VR.LO, "1234");
                dcmObj.putString(Tag.PatientName, dcmObj.vrOf(Tag.PatientName), "Shady Salah");
                dcmObj.putString(Tag.PatientID, dcmObj.vrOf(Tag.PatientID), "01284991399");
                dcmObj.putString(Tag.PatientAge, dcmObj.vrOf(Tag.PatientAge), "23");
                dcmObj.putString(Tag.PatientSex, dcmObj.vrOf(Tag.PatientSex), "Male");
                dcmObj.putString(Tag.StudyInstanceUID, dcmObj.vrOf(Tag.StudyInstanceUID), "1.2.3.4.5.6.7.8.9.10");
                dcmObj.putString(Tag.SOPInstanceUID, dcmObj.vrOf(Tag.SOPInstanceUID), " 20.30.40.50.60.70");
                dcmObj.putString(Tag.SOPInstanceUID, dcmObj.vrOf(Tag.SOPInstanceUID), " 12.13.14.15.16");

                EditDicomTags.writeFile(dcmObj, sourceFolder + "/", allFiles[i].getName());
                din.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } //Loop
        //get all files from tag file and delete them
    }

    public static void writeFile(DicomObject obj, String copyServer, String fileName) {

        File f = new File(copyServer + fileName);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        DicomOutputStream dos = new DicomOutputStream(bos);
        try {
            dos.writeDicomFile(obj);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            try {
                dos.close();
            } catch (IOException ignore) {
            }
        }
    }

    public static void main(String[] args) {

        UpdateTags("/home/shady/(1)Optimal/(3)Files/test");

        System.out.println(DicomUtil.getDicomTags("/home/shady/(1)Optimal/(3)Files/test/image-000001.dcm"));
    }
}
