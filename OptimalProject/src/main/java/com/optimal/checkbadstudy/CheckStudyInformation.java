package com.optimal.checkbadstudy;

import java.io.File;
import java.io.IOException;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.DicomInputStream.IncludeBulkData;

public class CheckStudyInformation {

    public CheckStudyInformation() {
    }

    public void checkStudyInformation(String dicomPath) {

        Attributes attr = null; //file dataset info

        File file = new File(dicomPath);

        DicomInputStream dis = null;
        try {

            dis = new DicomInputStream(file);
            dis.setIncludeBulkData(IncludeBulkData.URI);
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

}
