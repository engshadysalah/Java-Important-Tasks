package com.optimal.util;

import java.io.*;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mohamed Shehata
 */
public class FileUtil {

    public static void fileBufferedReader(String filePath) {

        File file = new File(filePath + File.separator);

        //read from file 
        //https://www.programcreek.com/2011/03/java-read-a-file-line-by-line-code-example/
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
            ex.printStackTrace();
        }
    }

    public static void fileBufferedWriter(String filePath) {

        File file = new File(filePath + File.separator);

        //write into file
        //https://www.programcreek.com/2011/03/java-write-to-a-file-code-example/
        try {
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            for (int i = 0; i < 10; i++) {
                bw.write("something");
                bw.append("shady");
                bw.newLine();
            }
            bw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static void fileWriter(String filePath) {

        FileWriter f0;
        try {

            String newLine = System.getProperty("line.separator");

            f0 = new FileWriter(filePath);

            for (int i = 0; i <= 100; i++) {
                f0.write(i + newLine);
            }

            f0.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    
    
       // reading txtFile over net
    public void readingFileOverNet() {

        InputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {

            URL url = new URL("http://services.......");

            inputStream = url.openStream();

            bufferedInputStream = new BufferedInputStream(inputStream);

            StringBuilder builder = new StringBuilder();

            while (true) {

                int data = bufferedInputStream.read();
                if (data == -1) {
                    break;
                } else {
                    builder.append((char) data);
                }
            }

            inputStream.close();
            bufferedInputStream.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    // get all files in directory and subDirectory
    public static List getFiles(String directoryPath) {

        ArrayList<File> files = new ArrayList<>();

        File directory = new File(directoryPath);

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

    // get information about the file
    public static void getFileInformation(String filePath) {

        File file = new File(filePath + File.separator);

        if (file.exists()) {

            System.out.println("exists");
            System.out.println("File Name : " + file.getName());
            System.out.println("pathe : " + file.getPath());
            System.out.println("the pathe of folder thate Contained file: " + file.getParent());
            System.out.println("the pathe of folder thate Contained file: " + file.getParentFile());
            System.out.println("File Length : " + file.length() + " bit");
            System.out.println("Total Size : " + file.getTotalSpace());
            System.out.println("usable space : " + file.getUsableSpace());
            System.out.println("Free space : " + file.getFreeSpace());

            String canewrite = file.canWrite() ? "Yes" : "NO";
            String caneread = file.canRead() ? "Yes" : "NO";
            String canExecuted = file.canExecute() ? "Yes" : "NO";
            System.out.println("Can Writable ? " + canewrite + "\n" + "Can Readable ? " + caneread + "\n" + "Can Executable ? " + canExecuted);

            System.out.println("Last Modified Date : " + new Date(file.lastModified()));

            String isFie = file.isFile() ? "Yes" : "NO";
            String isDirectory = file.isDirectory() ? "Yes" : "NO";
            System.out.println("Is File ? " + isFie + "\n" + "Is Directory ? " + isDirectory);

        } else {
            System.out.println("Your File isn't founded");
        }

    }

    //////////////////////////////////    (Tasks)   /////////////////////////////////////////
    // task : Write 100 Random dublicated  Numbers in file And Then Sorted them in another File
    public static void writeDublicatedRandomNumbers(String filePath) {

        int[] sortedNumbers = new int[100];

        FileWriter mainFile;
        FileWriter sortedFile;
        try {

            String newLine = System.getProperty("line.separator");

            mainFile = new FileWriter(filePath);
            sortedFile = new FileWriter(filePath + "Sorted");
            Random random = new Random();

            int randomNumber;
            for (int i = 0; i < sortedNumbers.length; i++) {
                randomNumber = random.nextInt(100);
                mainFile.write(randomNumber + newLine);
                sortedNumbers[i] = randomNumber;
            }

            Arrays.sort(sortedNumbers);

            for (int i = 0; i < sortedNumbers.length; i++) {
                System.out.println(sortedNumbers[i]);
                sortedFile.write(sortedNumbers[i] + newLine);

            }

            mainFile.close();
            sortedFile.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    // task : Write 100 Random NOTdublicated  Numbers in file And Then Sorted them in another File
    public static void writeNoDuplicate(String filePath) {

        int[] sortedNumbers = new int[100];

        FileWriter mainFile;
        FileWriter sortedFile;
        try {

            String newLine = System.getProperty("line.separator");

            mainFile = new FileWriter(filePath);
            sortedFile = new FileWriter(filePath + "Sorted");

            Integer[] arr = new Integer[100];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = i;
            }
            Collections.shuffle(Arrays.asList(arr));

            int randomNumber;
            for (int i = 0; i < sortedNumbers.length; i++) {
                randomNumber = arr[i];
                System.out.println(arr[i]);
                mainFile.write(randomNumber + newLine);
                sortedNumbers[i] = randomNumber;
            }

            Arrays.sort(sortedNumbers);

            for (int i = 0; i < sortedNumbers.length; i++) {
                // System.out.println(sortedNumbers[i]);
                sortedFile.write(sortedNumbers[i] + newLine);
            }

            mainFile.close();
            sortedFile.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    // task : remove Multiples of 5 from mainFile and add them to MultipleFile and add other number to newFile
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

    ///////////////////////////////////   (In Futuer)   /////////////////////////////////////////
    public static String getPublicIP() {
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));

            String ip = in.readLine(); //you get the IP as a String
            System.out.println(ip);
            return ip;

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    private static String readMACAddress() {

        try {

            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            while (networks.hasMoreElements()) {
                NetworkInterface network = networks.nextElement();
                System.out.println("network ID : " + network.getDisplayName());
                byte[] mac = network.getHardwareAddress();

                if (mac != null) {

                    System.out.print("Current MAC address : ");

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    System.out.println(sb.toString());
                    return sb.toString();
                }
            }

        } catch (SocketException e) {

            e.printStackTrace();

        }
        return null;

    }

    public static String getStationUID() {
        if (System.getProperty("os.name").equals("Linux")) {

            return readMACAddress();

        } else {
            try {
                Process process = Runtime.getRuntime().exec(new String[]{"wmic", "baseboard", "get", "serialnumber"});
                Scanner sc = new Scanner(process.getInputStream());
                String property = sc.next();
                String baseBoardID = sc.next();
                System.out.println("baseBoardID " + baseBoardID);
                return baseBoardID;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
