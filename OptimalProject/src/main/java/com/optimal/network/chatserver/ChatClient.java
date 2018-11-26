package com.optimal.network.chatserver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.miginfocom.swing.MigLayout;

public class ChatClient extends JFrame implements Runnable {

    private static JPanel chatPanel;
    private JTextArea type;
    private JButton Send;
    private JButton emoj1;
    private JButton emoj2;
    private JButton emoj3;
    private JButton emoj4;
    private JButton emoj5;
    private JPanel upper;
    //private JPanel lower;
    // private BorderLayout bl;
    JScrollPane jsp;

    JLabel container;
    JLabel displayImgLable;

    private Scanner console;					// input from the user
    private ChatClientCommunicator comm;		// communication with the server
    private boolean hungup = false;				// has the server hung up on us?

    private Socket sock;

    Thread thread = new Thread(this);

//    static String emojPath = "/home/shady/Desktop/chat_pich/";
    static String emojPath = "images/";

    static JList privateClientsList;
    JScrollPane privateClientsListSP;
    private String pcli[] = {"Public", "c1", "c2", "c3"};

    static DefaultListModel<String> model;
    static int x;

    int privateClientID;
    boolean selectedPrivateChat;

    public ChatClient(Socket sock) {

        this.sock = sock;

    }

    @Override
    public void run() {

        try {

            // bl = new BorderLayout();
            upper = new JPanel();
            upper.setLayout(new MigLayout());
            // lower = new JPanel();
            chatPanel = new JPanel();
            chatPanel.setLayout(new MigLayout());

            // chatPanel.setEditable(false);
            //  chatPanel.setLineWrap(true);
            type = new JTextArea(3, 25);
            //  jsp = new JScrollPane(chatPanel);
            //jsp.setSize(500, 400);

            displayImgLable = new JLabel();
            // new SmartScroller(jsp);
            //Send = new JButton( new ImageIcon(ImageIO.read(getClass().getResource("sen.jpg"))));

            // System.out.println(getClass().getResource("ChatClient.java"));
            //getClass().getResource
            // System.out.println(" paths :: " + ChatClient.class.getClass().getResource("images/sen.jpg"));
             System.out.println(" paths :: " + ChatClient.class.getClass().getResource("/images/sen.jpg"));
            Send = new JButton(new ImageIcon(ChatClient.class.getClass().getResource("/images/sen.jpg")));
            emoj1 = new JButton(new ImageIcon(getClass().getResource("/images/e1.jpg")));
            emoj2 = new JButton(new ImageIcon(getClass().getResource("/images/e2.jpg")));
            emoj3 = new JButton(new ImageIcon(getClass().getResource("/images/e3.jpg")));
            emoj4 = new JButton(new ImageIcon(getClass().getResource("/images/e4.png")));
            emoj5 = new JButton(new ImageIcon(getClass().getResource("/images/e5.png")));

            
            privateClientsList = new JList(pcli);
            privateClientsListSP = new JScrollPane();
            privateClientsListSP.getViewport().add(privateClientsList, null);
            privateClientsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            //   privateClientsList.add(new JLabel("Test :: new lable"));
            //  model = new DefaultListModel<String>();
            //   model.addElement("shady");
            // System.out.println("mmmoddel ::  " + model);
            // privateClientsList.setModel(model);
            upper.add(chatPanel, "  span9");
            //upper.add(jsp, "  span9");

            upper.add(privateClientsListSP, "wrap");

            upper.add(type, "split");
            upper.add(Send, "wrap");
            upper.add(emoj1, "split");
            upper.add(emoj2, "");
            upper.add(emoj3, " ,  ");
            upper.add(emoj4, "split");
            upper.add(emoj5, "wrap");

            add(upper);

            privateClientsList.addListSelectionListener(new ListSelectionListener() {

                @Override
                public void valueChanged(ListSelectionEvent e) {

                    System.out.println("slected from clients list :: " + privateClientsList.getSelectedIndex());

                    if (privateClientsList.getSelectedIndex() <= 0) {

                        privateClientsList.clearSelection();
                        System.out.println("Start Public chat  " + selectedPrivateChat + " With Client" + privateClientID);
                        selectedPrivateChat = false;
                    } else {
                        privateClientID = privateClientsList.getSelectedIndex();
                        selectedPrivateChat = true;
                        System.out.println("Start private chat   " + selectedPrivateChat + " With Client" + privateClientID);
                    }
                }
            });

            Send.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (type.getText().equals("")) {
                        System.out.println("no text entered");
                    } else {

                        if (selectedPrivateChat) {
                            System.out.println("submitted Public Chat");
                            comm.send(type.getText() + " private to " + (privateClientID - 1));
                            //selectedPrivateChat = false;
                        } else {
                            System.out.println("submitted Public Chat");

                            comm.send(type.getText());
                        }
                        type.setText("");

                    }
                }
            });

            emoj1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    type.append(":) ");
                }
            });

            emoj2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    type.append(":( ");
                }
            });

            emoj3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    type.append("3> ");
                }
            });

            emoj4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    type.append("-_- ");
                }
            });

            emoj5.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    type.append("*_* ");
                }
            });

            this.setSize(400, 600);
            this.setTitle("MattChatt");
            this.setVisible(true);
            // this.setResizable(false);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);

            // For reading lines from the console
            console = new Scanner(System.in);

            // Fire off a new thread to handle incoming messages from server
            comm = new ChatClientCommunicator(sock, this);
            comm.setDaemon(true);
            comm.start();

            //to write from Scanner
            //   handleUser();
        } catch (IOException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // to start my thread
    public void startMythread() {
        thread.start();
    }

    // Get input from console and send it to server; stop & clean up when server
    // has hung up (noted by hungup)
    public void handleUser() throws IOException {
        while (!hungup) {
            comm.send(console.nextLine());
        }
    }

    //  the server has hung up (so handleUser loop will terminate)
    public void hangUp() {
        hungup = true;
    }

    JLabel ll;
    List<JLabel> eachMessageLabels = new ArrayList<>();
    List<JLabel> textLabels = new ArrayList<>();

    public static void activeClients(String clientName) {

        System.out.println("activeClients ============> " + clientName);

//        privateClientsList.add(new JLabel(clientName));
        // privateClientsList.repaint();
        System.out.println("");

//        model.addElement("yarab");
//        privateClientsList.setModel(model);
        // DefaultListModel<String> model = new DefaultListModel<String>();
        //  model.addElement("aded");
        // System.out.println("mmmoddel ::  " + model);
//        privateClientsList.setModel(model);
    }

    public void showMessage(String mes) {

//        System.out.println("before");
//        System.out.println("====>" + mes + "<=====");
        String[] emoj_chek = {":)", ":(", "3>", "-_-", "*_*"};
        List<String> split_Message = Arrays.asList(mes.split(" "));
        List<String> emojs = new ArrayList<String>();

        // String[] Message_without_emoj = new String[split_Message.length];
        //StringBuilder Message_without_emoj = new StringBuilder();
        // Set Message_without_emoj = new HashSet();
        StringBuilder NewMessage_without_emoj = new StringBuilder();

        int j;
        for (int i = 0; i < split_Message.size(); i++) {

            for (j = 0; j < emoj_chek.length; j++) {

                if (split_Message.get(i).equals(emoj_chek[j])) {

                    emojs.add(split_Message.get(i));

                }
            }

        }
        System.out.println("only emojan is -====> " + emojs);

        eachMessageLabels.clear();

        for (int i = 0; i < split_Message.size(); i++) {

            eachMessageLabels.add(new JLabel());

        }

        System.out.println("size lable :" + eachMessageLabels.size());
        System.out.println(split_Message);

        for (int i = 0; i < split_Message.size(); i++) {

            System.out.println("sp " + split_Message.get(i));
            if (split_Message.get(i).equals(":)")) {

                // JLabel CC = new JLabel();
                // ll.setIcon(null);
                System.out.println(eachMessageLabels.get(i));
                chatPanel.add(eachMessageLabels.get(i), " split");
                System.out.println("tttttest");
                eachMessageLabels.get(i).setIcon(getEmojImage("e1.jpg"));
                System.out.println("Path :: " + ChatClient.class.getResource(emojPath + "e1.jpg"));

            } else if (split_Message.get(i).equals(":(")) {

                //ll = new JLabel();
                //ll.setIcon(null);
                chatPanel.add(eachMessageLabels.get(i), " split");
                eachMessageLabels.get(i).setIcon(getEmojImage("e2.jpg"));
                System.out.println("done :( ");
////                // chatPanel.append(NewMessage_without_emoj + "\n");
////                if (NewMessage_without_emoj.length() != 0) {
////                    eachMessageLabels.get(i).setText(NewMessage_without_emoj + "");
////                }
                //  eachMessageLabels.add(ll);
            } else if (split_Message.get(i).equals("3>")) {

                chatPanel.add(eachMessageLabels.get(i), " split ");
                eachMessageLabels.get(i).setIcon(getEmojImage("e3.jpg"));
////                if (NewMessage_without_emoj.length() != 0) {
////                    eachMessageLabels.get(i).setText(NewMessage_without_emoj + "");
////                }
////                // displayImgLable.setIcon(new ImageIcon(emojPath + "e3.jpg"));
                System.out.println("done 3> ");
                // chatPanel.append(NewMessage_without_emoj + "\n");

            } else if (split_Message.get(i).equals("-_-")) {

                chatPanel.add(eachMessageLabels.get(i), " split ");
                eachMessageLabels.get(i).setIcon(getEmojImage("e4.png"));
//
//                if (NewMessage_without_emoj.length() != 0) {
//                    eachMessageLabels.get(i).setText(NewMessage_without_emoj + "");
//                }

                //  displayImgLable.setIcon(new ImageIcon(emojPath + "e4.png"));
                System.out.println("done -_- ");
                // chatPanel.append(NewMessage_without_emoj + "\n");
                eachMessageLabels.get(i).setText(NewMessage_without_emoj + "");

            } else if (split_Message.get(i).equals("*_*")) {

                chatPanel.add(eachMessageLabels.get(i), " split ");
                eachMessageLabels.get(i).setIcon(getEmojImage("e5.png"));

//                if (NewMessage_without_emoj.length() != 0) {
//                    eachMessageLabels.get(i).setText(NewMessage_without_emoj + "\n");
//                }
                //displayImgLable.setIcon(new ImageIcon(emojPath + "e5.png"));
                System.out.println("done *_* ");
                // chatPanel.append(NewMessage_without_emoj + "\n");
            } else {

                if (emojs.isEmpty()) {

                    chatPanel.add(eachMessageLabels.get(0));
                    eachMessageLabels.get(0).setText(mes);

                } else {
                    // System.out.println("==>"+split_Message.get(i));
                    chatPanel.add(eachMessageLabels.get(i), "split");
                    eachMessageLabels.get(i).setText(split_Message.get(i));
                }
            }

        }

//        if (emojs.isEmpty()) {
//
//            // for (int i = 0; i < emojs.size(); i++) {
//            JLabel t = new JLabel();
//            chatPanel.add(t, "wrap");
//            t.setText(mes + "\n");
//
//            //}
//            //chatArea.append(mes + "\n");
//        } else {
        JLabel newLine = new JLabel();
        chatPanel.add(newLine, "wrap");
        //}

    }

    ImageIcon getEmojImage(String emojName) {

        return new ImageIcon(ChatClient.class.getResource("/images/" + emojName));
    }

    public static void main(String[] args) throws IOException {

        ChatClient client = new ChatClient(new Socket("localhost", 4242));
        client.startMythread();

        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//
//        ChatClient cc = new ChatClient(new Socket("localhost", 4242));
//        cc.startMythread();
//
//        ChatClient cc2 = new ChatClient(new Socket("localhost", 4242));
//        cc2.startMythread();

    }

}
