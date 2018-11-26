/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.dicom.getalldicomtags;

import com.optimal.dbmodel.Employee;
import com.optimal.util.DicomUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.tools.framedump;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.util.TagUtils;
import org.dcm4che3.data.Tag;

/**
 *
 * @author root
 */
public class ReadAllDicomTags {

    JFrame frame;
    JPanel jPanel;
    JTablDicomTags jt;
    JScrollPane sp;

    public ReadAllDicomTags() {

        frame = new JFrame();
        jPanel = new JPanel();
        jt = new JTablDicomTags();
        sp = new JScrollPane(jt);

        frame.add(jPanel);
        jPanel.add(sp);

        frame.pack();
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        jt.setTableFromDicomTags((ArrayList<DicomTags>) getListDicomHeader("/home/shady/(1)Optimal/(3)Files/test/image-000001.dcm"));
    }

    public static List getListDicomHeader(String dicomPath) {

        DicomObject object = null;
        try {
            DicomInputStream dis = new DicomInputStream(new File(dicomPath));
            object = dis.readDicomObject();
            dis.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return ListDicomHeader(object);
    }

    public static List ListDicomHeader(DicomObject dcmObj) {

        ArrayList<DicomTags> dicomTagsList = new ArrayList<>();

        Iterator iter = dcmObj.datasetIterator();
        while (iter.hasNext()) {
            DicomElement element = (DicomElement) iter.next();
            int tag = element.tag();
            try {
                String tagName = dcmObj.nameOf(tag);
                String tagAddr = TagUtils.toString(tag);
                String tagVR = dcmObj.vrOf(tag).toString();

                DicomTags dicomTags = new DicomTags();
                dicomTags.setTagName(tagName);
                dicomTags.setTagAddr(tagAddr);
                dicomTags.setTagVR(tagVR);
                dicomTags.setTag(tag);

                dicomTagsList.add(dicomTags);
                // dicomTagsList.add();

                if (tagVR.equals("SQ")) {
                    if (element.hasItems()) {
                        System.out.println(tagAddr + " [" + tagVR + "] " + tagName);

                        DicomTags geoupDicomTags = new DicomTags();
                        geoupDicomTags.setTagName(tagName);
                        geoupDicomTags.setTagAddr("        "+tagAddr);
                        geoupDicomTags.setTagVR(tagVR);
                        geoupDicomTags.setTag(tag);
                        dicomTagsList.add(geoupDicomTags);
 
                        continue;
                    }
                }

                String tagValue = dcmObj.getString(tag);
                dicomTags.setTagValue(tagValue);

                System.out.println(tagAddr + " [" + tagVR + "] " + tagName + " [" + tagValue + "]");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return dicomTagsList;
    }

    public static void main(String[] args) {

        new ReadAllDicomTags();
//
//        String s="(0028,0011)";
//        String b=s.replace("(", "");
//        String c=b.replace(")", "");
//        String[] part1=c.split(",");
//        
        //int id=Integer.parseInt(c);

//        System.out.println(c);
        // System.out.println(DicomUtil.getDicomTags("/home/shady/(1)Optimal/(3)Files/series-000001/image-000001.dcm"));
        //test from DicomUtail
        // DicomUtil.getListDicomHeader("/home/shady/(1)Optimal/(3)Files/test/image-000001.dcm");
        //test from this classe
        // getListDicomHeader("/home/shady/(1)Optimal/(3)Files/test/image-000001.dcm");
    }
}
