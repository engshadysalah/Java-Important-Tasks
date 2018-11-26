/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.util;

import com.optimal.dicom.EditDicomTags;
import com.optimal.dicom.ReadDicomTags;
import com.optimal.dicom.ShowDicom;
import ij.IJ;
import ij.plugin.DICOM;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import org.dcm4che2.data.BasicDicomObject;
import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.VR;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.io.DicomOutputStream;
import org.dcm4che2.util.TagUtils;
import org.dcm4che3.data.Attributes;

import org.dcm4che3.data.Tag;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReaderSpi;

//import org.dcm4che2.imageioimpl.plugins.dcm.DicomImageReaderSpi;
/**
 *
 * @author root
 */
public class DicomUtil {

    ////////////
    ///// DICOM
    public static String getDicomTags(String dicomPath) {

        DicomObject dcmObj = null;
        DicomInputStream din = null;
        String data = null;
        try {

            din = new DicomInputStream(new File(dicomPath));

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

    ////////////////////////////////(update tags of dicom and write it agin)
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

    /// update specific tagsValue
    public static void UpdateSpecificTags(String sourceFolder, int tag, String newTagValue) {

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
                dcmObj.putString(tag, dcmObj.vrOf(tag), newTagValue);

                System.out.println("test : " + dcmObj.getString(tag));

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

    ///////////////////////////////(get all tages of Dicom)
    //http://samucs.blogspot.com.eg/2009/03/listing-dicom-header-information-with.html
    public static void getListDicomHeader(String dicomPath) {

        DicomObject object = null;
        try {
            DicomInputStream dis = new DicomInputStream(new File(dicomPath));
            object = dis.readDicomObject();
            dis.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        listDicomHeader(object);
    }

    public static void listDicomHeader(DicomObject dcmObj) {

        Iterator iter = dcmObj.datasetIterator();
        while (iter.hasNext()) {
            DicomElement element = (DicomElement) iter.next();
            int tag = element.tag();
            try {
                String tagName = dcmObj.nameOf(tag);
                String tagAddr = TagUtils.toString(tag);
                String tagVR = dcmObj.vrOf(tag).toString();
                if (tagVR.equals("SQ")) {
                    if (element.hasItems()) {
                        System.out.println(tagAddr + " [" + tagVR + "] " + tagName);

                        listDicomHeader(element.getDicomObject());

                        continue;
                    }
                }

                String tagValue = dcmObj.getString(tag);
                System.out.println(tagAddr + " [" + tagVR + "] " + tagName + " [" + tagValue + "]");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /////////////////////////////////
    public static String getSOPInstanceUID(String dicomPath) {

        DicomObject dcmObj = null;
        DicomInputStream din = null;
        String sopInstanceUID = null;
        try {

            din = new DicomInputStream(new File(dicomPath));

            dcmObj = din.readDicomObject();

            sopInstanceUID = dcmObj.getString(Tag.SOPInstanceUID);

        } catch (IOException ex) {
            Logger.getLogger(ReadDicomTags.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sopInstanceUID;

    }

    ////////////////////////////////////////////////////////////////
    // tele-cloud handled bad studies - missing info
    public void checkStudyInformation(String dicomPath) {

        Attributes attr = null; //file dataset info

        File file = new File(dicomPath);

        org.dcm4che3.io.DicomInputStream dis = null;
        try {

            dis = new org.dcm4che3.io.DicomInputStream(file);
            dis.setIncludeBulkData(org.dcm4che3.io.DicomInputStream.IncludeBulkData.URI);
            attr = dis.readDataset(-1, -1);

            // check methoud push attr  read all dicom tags if one of them is null then delet the dicom
            if (readStudyInformation(attr)) {

                System.out.println("Dicom Must be deleted ");

                if (file.delete()) {
                    System.out.println("Bad Study is deleted");
                };
            }

        } catch (IOException e) {

            System.out.println(e.getMessage());

            if (file.delete()) {
                System.out.println(" Not a DICOM Stream is deleted");
            };

        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
            } catch (IOException ignore) {
                System.out.println(ignore.getMessage());
            }
        }

    }

    static boolean readStudyInformation(Attributes attr) {

        boolean badStudy = false;

        //check Study Info
        if (attr.getString(Tag.PatientID) == null || attr.getString(Tag.AccessionNumber) == null || attr.getString(Tag.AdditionalPatientHistory) == null || attr.getString(Tag.AdmittingDiagnosesDescription) == null
                || attr.getString(Tag.ReferringPhysicianName) == null || attr.getDate(Tag.StudyDateAndTime) == null
                || attr.getString(Tag.StudyID) == null || attr.getString(Tag.StudyDescription) == null || attr.getString(Tag.StudyPriorityID) == null
                || attr.getString(Tag.StudyStatusID) == null) {

            System.out.println("bad study");
            badStudy = true;
        } else {
            System.out.println("good study");
        }

        return badStudy;
    }

    ////////////////////////////////////////////////////////////////////
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

    public static BufferedImage getDicomDCM(String dicomPath) {

        ImageReader reader;
        BufferedImage displayedImage = null;

        try {
            reader = new DicomImageReaderSpi().createReaderInstance();

            FileImageInputStream input = new FileImageInputStream(new File(dicomPath));
            reader.setInput(input);

            displayedImage = reader.read(0);

        } catch (IOException ex) {
            Logger.getLogger(ImageUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return displayedImage;
    }

    // stop here 
    public static BufferedImage getDecompressDicomByGCDM(String dicomPath) {

        BufferedImage displayedImage = null;
//
//        String[] array = dicomPath.split("/");
//        String dicomName = null;
//
//        for (int i = 0; i < array.length; i++) {
//            dicomName = array[array.length - 1];
//        }
//
//        System.out.println("Image Name = " + dicomName);
//
//        ImageReader reader = new ImageReader();
//        reader.SetFileName(dicomName);
//
//        boolean ret = reader.Read();
//
//        if (!ret) {
//            System.out.println(" Could not read : ");
//        }
//
//        ImageChangeTransferSyntax change = new ImageChangeTransferSyntax();
//        change.SetTransferSyntax(new TransferSyntax(TransferSyntax.TSType.ImplicitVRLittleEndian));
//        change.SetInput(reader.GetImage());
//
//        if (!change.Change()) {
//            System.out.println(" Could not change: ");
//        }
        //    Image out = change.GetOutput();
//        System.out.println(out.toString());
//

        //  displayedImage=  new BufferedImage (out.getWidth(null),out.getHeight(null),BufferedImage.TYPE_INT_RGB);
        // displayedImage = (BufferedImage) out;  
//        // Set the Source Application Entity Title
//        FileMetaInformation.SetSourceApplicationEntityTitle("Just For Fun");
//
//        // converted to Bufered Image
//        ImageWriter writer = new ImageWriter();
//        writer.canWriteEmpty();
//        writer.SetFileName(file2);
//        writer.SetFile(reader.GetFile());
//        writer.SetImage(out);
//       
        //ret = writer.Write();
//        if (!ret) {
//            System.out.println("Could not write: ");
// 
        // displayedImage = ImageIO.read(new File(imagePath));   
        // }
//        
        return null;
    }

    public BufferedImage getDecompressDicom(String dicomPath) {

        //http://forums.dcm4che.org/jiveforums/thread.jspa?threadID=2585
//        
//        DicomImageReaderSpi dicomImageReaderSpi=new DicomImageReaderSpi();
//        ImageReader createReaderInstance = dicomImageReaderSpi.createReaderInstance(dicomPath);
//
//  
//        
//        
//        try {
//            ImageReaderSpi imageReaderSpi=new ImageReaderSpi() {
//                @Override
//                public boolean canDecodeInput(Object source) throws IOException {
//                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//                }
//
//                @Override
//                public ImageReader createReaderInstance(Object extension) throws IOException {
//                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//                }
//
//                @Override
//                public String getDescription(Locale locale) {
//                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//                }
//            };
//            imageReaderSpi.createReaderInstance();
//            
//        
//   
//           
//            
//            
//        } catch (IOException ex) {
//            Logger.getLogger(DicomUtil.class.getName()).log(Level.SEVERE, null, ex);
//        }
//            
        return null;

    }

}
