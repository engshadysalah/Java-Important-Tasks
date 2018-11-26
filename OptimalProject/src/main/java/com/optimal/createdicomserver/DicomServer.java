package com.optimal.createdicomserver;

import com.google.common.eventbus.EventBus;
import com.optimal.createdicomserver.event.NewFileEvent;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.DicomInputStream.IncludeBulkData;
import org.dcm4che3.io.DicomOutputStream;
import org.dcm4che3.net.*;
import org.dcm4che3.net.pdu.AAssociateAC;
import org.dcm4che3.net.pdu.AAssociateRQ;
import org.dcm4che3.net.pdu.PresentationContext;
import org.dcm4che3.net.pdu.UserIdentityAC;
import org.dcm4che3.net.service.BasicCEchoSCP;
import org.dcm4che3.net.service.BasicCStoreSCP;
import org.dcm4che3.net.service.DicomServiceRegistry;
import org.dcm4che3.util.SafeClose;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class DicomServer {

    // private static final Logger LOG = LoggerFactory.getLogger(DicomServer.class);
    private static final String DCM_EXT = ".dcm";

    private final Device device = new Device("storescp");
    private final ApplicationEntity ae = new ApplicationEntity("*");
    private final Connection conn = new Connection();
    private File storageDir;

    private int status;

    public EventBus eventBus;

    private final class CStoreSCPImpl extends BasicCStoreSCP {

        CStoreSCPImpl() {
            super("*");
        }

        @Override
        protected void store(Association as, PresentationContext pc,
                Attributes rq, PDVInputStream data, Attributes rsp)
                throws IOException {
            // unsigned short
            rsp.setInt(Tag.Status, VR.US, status);  //Association Response Parameteres

            if (storageDir == null) {
                return;
            }

            String ipAddress = as.getSocket().getInetAddress().getHostAddress(); //ip address // of target AE (packs)
            String associationName = as.toString(); // target AE title 
            String cuid = rq.getString(Tag.AffectedSOPClassUID); //  Association Request Parameteres: (ClassUID) 
            String iuid = rq.getString(Tag.AffectedSOPInstanceUID); //   Association Request Parameteres:  (InstanceUID)
            String tsuid = pc.getTransferSyntax(); // Transfer syntax : defines how DICOM objects are serialized. When holding an object in memory

            //File file = new File(storageDir, ipAddress + "_" + iuid + DCM_EXT);
            File file = new File(storageDir, iuid + DCM_EXT);  // create dicomFile
            try {
                // LOG.info("as: {}", as); // show in log
                System.out.println("as:" + as);
                //
                storeTo(as, as.createFileMetaInformation(iuid, cuid, tsuid),
                        data, file);

                if (!file.exists()) {
                    System.out.println("File  " + file.getAbsolutePath() + "does not exists! "
                            + "Connection Details--> ipAddress: " + ipAddress
                            + " associationName: " + associationName
                            + "sopclassuid:  " + cuid
                            + "sopinstanceuid: " + iuid
                            + "transfersyntax: " + tsuid);

                    return;
                }

                eventBus.post(new NewFileEvent(file));

                //let's parse the files
                /*Attributes attrs = parse(file);
                if(attrs != null){                	
                	String studyiuid = attrs.getString(Tag.StudyInstanceUID);
	         		String patientID = attrs.getString(Tag.PatientID);
	         		patientID = (patientID == null || patientID.length() == 0) ? "<UNKNOWN>" : patientID;
	         		Long projectID = -1L;
	         		String patientName = attrs.getString(Tag.PatientName);
	         		String institutionName = attrs.getString(Tag.InstitutionName);
	         		String uniqueID = file.getName();
	         		Date studyDate =  attrs.getDate(Tag.StudyDate);
	         		Date studyTime =  attrs.getDate(Tag.StudyTime);         		
	         		
	         		String studyDateTime = (studyDate != null && studyTime != null)?new SimpleDateFormat("MM-dd-yyyy").format(studyDate)+" "+new SimpleDateFormat("HH-mm-ss").format(studyTime):"01-01-1901 01-01-01";
	         	
	         		
	         		
                	//eventBus.post(new NewLogEvent(as.toString(), "IMAGE_RECEIVED", ipAddress, studyDateTime, projectID, patientID, patientName, null, studyiuid, institutionName, uniqueID));
                	//eventBus.post(new NewFileEvent(file, ipAddress, studyiuid, iuid, cuid, associationName));
                	
                }else{
                	LOG.error("File Name {} could not be parsed!",file.getName());
                }*/
            } catch (EOFException e) { //EOFException - if this input stream reaches the end before reading eight bytes.
                deleteFile(as, file); //broken file, just remove...     
                System.out.println("Dicom Store EOFException: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    public DicomServer() throws IOException {
        device.setDimseRQHandler(createServiceRegistry()); // create dicom service
        device.addConnection(conn); //establish connection
        device.addApplicationEntity(ae); // called AE Title (mark is *   ===> that refers to any name of ae title) 
        device.setAssociationHandler(associationHandler); // like calling AE make a session with Target AE 
        ae.setAssociationAcceptor(true);
        ae.addConnection(conn);
    }

    // done : 
    //mkdirs in storageDir 
    //write as.createFileMetaInformation(iuid, cuid, tsuid) -Attributes- to dicomFile
    // copy data to dicomFile
    private void storeTo(Association as, Attributes fmi,
            PDVInputStream data, File file) throws IOException {
        System.out.println(as + " : M-WRITE " + file);
        file.getParentFile().mkdirs();
        DicomOutputStream out = new DicomOutputStream(file);
        try {
            out.writeFileMetaInformation(fmi);
            data.copyTo(out);
        } finally {
            SafeClose.close(out);
        }
        //
        //
        //
        //
        /// dicom file
        /// check  
        DicomReader dicomReade = new DicomReader(file);
        System.out.println(dicomReade);

    }

    private static Attributes parse(File file) throws IOException {
        DicomInputStream in = new DicomInputStream(file);
        try {
            in.setIncludeBulkData(IncludeBulkData.NO);
            return in.readDataset(-1, Tag.PixelData);
        } finally {
            SafeClose.close(in);
        }
    }

    // done but need delete from db 
    private static void deleteFile(Association as, File file) {
        if (file.delete()) {
            System.out.println(as + ":  M-DELETE  " + file);
        } else {
            System.out.println(as + " : M-DELETE " + file + " failed!");

        }
    }

    // done
    // create dicom service
    private DicomServiceRegistry createServiceRegistry() {
        DicomServiceRegistry serviceRegistry = new DicomServiceRegistry();
        serviceRegistry.addDicomService(new BasicCEchoSCP()); // for connection (then respons is accept)
        serviceRegistry.addDicomService(new CStoreSCPImpl()); // then calling AE tell target AE to save object 
        return serviceRegistry;
    }

    public void setStorageDirectory(File storageDir) {
        if (storageDir != null) {
            storageDir.mkdirs();
        }
        this.storageDir = storageDir;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static void configureConn(Connection conn) {
        conn.setReceivePDULength(Connection.DEF_MAX_PDU_LENGTH);
        conn.setSendPDULength(Connection.DEF_MAX_PDU_LENGTH);

        conn.setMaxOpsInvoked(0);
        conn.setMaxOpsPerformed(0);
    }

    public static DicomServer init(String aeHost, int aePort, String aeTitle, String storageDirectory, EventBus eventBus) {

        System.out.println("Bind to: " + aeTitle + "@" + aeHost + ":" + aePort + "; storage: " + storageDirectory);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String currentDate = dateFormat.format(date);

        DicomServer ds = null;
        try {
            ds = new DicomServer();

            ds.eventBus = eventBus;
            if (aeHost != null) {
                ds.conn.setHostname(aeHost);
            }
            ds.conn.setPort(aePort);
            ds.ae.setAETitle(aeTitle);

            //default conn parameters
            configureConn(ds.conn);

            //accept-unknown
            ds.ae.addTransferCapability(
                    new TransferCapability(null,
                            "*",
                            TransferCapability.Role.SCP,
                            "*"));

            ds.setStorageDirectory(new File(storageDirectory + "_" + currentDate));

            ExecutorService executorService = Executors.newCachedThreadPool();
            ScheduledExecutorService scheduledExecutorService
                    = Executors.newSingleThreadScheduledExecutor();
            ds.device.setScheduledExecutor(scheduledExecutorService);
            ds.device.setExecutor(executorService);
            ds.device.bindConnections(); // 

        } catch (Exception e) {
            System.out.println("dicomserver: " + e.getMessage());
            e.printStackTrace();
        }

        return ds;
    }

    private AssociationHandler associationHandler = new AssociationHandler() {

        @Override
        protected AAssociateAC makeAAssociateAC(Association as,
                AAssociateRQ rq, UserIdentityAC arg2) throws IOException {

            State st = as.getState();

            if (as != null) {
                System.out.println("makeAAssociateAC: " + as.toString() + " Associate State:" + st + "  Associate State Name:" + st.name());

                try {
                    //eventBus.post(new NewLogEvent(as.toString(),st.name(),as.getSocket().getInetAddress().getHostAddress(), null, null,null,null,null,null,null,null));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            if (rq != null) {
                System.out.println("Max OpsInvoked:" + rq.getMaxOpsInvoked() + " Max OpsPerformed:" + rq.getMaxOpsPerformed() + " Max PDU Length:" + rq.getMaxPDULength() + "Number of Pres. Contexts:" + rq.getNumberOfPresentationContexts());

            }

            if (arg2 != null) {
                System.out.println("UserIdentityAC Length:" + arg2.length());
            }

            return super.makeAAssociateAC(as, rq, arg2);
        }

        // first part of Associate (setting Associate)
        @Override
        protected AAssociateAC negotiate(Association as, AAssociateRQ rq)
                throws IOException {

            if (as != null) {
                System.out.println("AAssociateAC negotiate: " + as.toString());
            }

            return super.negotiate(as, rq);
        }

        // close session
        @Override
        protected void onClose(Association as) {

            State st = as.getState();

            if (as != null && st == State.Sta13) {
                System.out.println("Assocation Released and Closed: " + as.getState() + " Name: " + as.toString());

                try {
                    //eventBus.post(new NewLogEvent(as.toString(),st.name(),as.getSocket().getInetAddress().getHostAddress(), null, null, null, null,null,null,null,null));
                } catch (Exception e) {
                    System.out.println(e.getMessage());

                }
            } else {
                System.out.println("Association Closed");

            }

            super.onClose(as);
        }
    };
}
