/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.imagegraphics;

import com.optimal.dicom.getalldicomtags.DicomTags;
import com.optimal.dicom.getalldicomtags.JTablDicomTags;
import static com.optimal.dicom.getalldicomtags.ReadAllDicomTags.getListDicomHeader;
import com.optimal.util.DicomUtil;
import com.optimal.util.FileUtil;
import com.optimal.util.ImageUtil;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author root
 */
// https://stackoverflow.com/questions/23457754/how-to-flip-bufferedimage-in-java
//https://stackoverflow.com/questions/15779877/rotate-bufferedimage-inside-jpanel
public class Image {

    JFrame frame;
    JPanel container;
    JPanel imagePanel;
    JTablDicomTags jt;
    JScrollPane sp;
    JButton selectImgage;
    JButton increaeRotaionAngel;
    JButton decreaeRotaionAngel;
    JButton monochrom;
    JButton filterBlure;
    JButton horizentalFlippedDown;
    JButton horizentalFlippedUp;
    JButton verticalFlippedRight;
    JButton verticalFlippedLeft;
    JButton updateTagValue;
    static JTextField newTagValueEditText;

    JLabel showRGB;

    BufferedImage displayedImage;

    String souraceFolder;

    int rotaionValue;

    boolean horizentalFlipedDown;
    boolean horizentalFlipedUp;
    boolean verticalFlipedRight;
    boolean verticalFlipedLeft;
    boolean monoChrom;
    boolean filterBlur;

    int nextImg = 0;
    String imagePath;
    List files;

    static int tag;

    public Image() {

        frame = new JFrame();

        container = new JPanel(new MigLayout());
        imagePanel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                if (displayedImage != null) {

                    // Rotaion
                    BufferedImage displayRotatedImage = ImageUtil.getplayRotatedImage(displayedImage, g, rotaionValue);
                    g.drawImage(displayRotatedImage, 0, 0, null);

                    if (monoChrom == true) {

                        //monochrom : (black and wight)
                        BufferedImage getplayMonoChoromImage = ImageUtil.getplayMonoChoromImage(displayedImage);
                        g.drawImage(getplayMonoChoromImage, 0, 0, null);
                        g.dispose();

                        monoChrom = false;
                    } else if (filterBlur == true) {

                        //filter blure
                        BufferedImage getFilteredBlureImage = ImageUtil.getFilteredBlureImage(displayedImage, 20, 20);
                        g.drawImage(getFilteredBlureImage, 0, 0, null);
                        g.dispose();
                        filterBlur = false;
                    } else if (horizentalFlipedDown == true) {

                        // vertical flip :  Hight
                        BufferedImage displayVerticalFlipImage = ImageUtil.getHorizentalFlippedImageDown(displayedImage, g);
                        g.drawImage(displayVerticalFlipImage, 0, 0, null);
                        g.dispose();

                        horizentalFlipedDown = false;
                    } else if (horizentalFlipedUp == true) {

                        //vertical flip : Width
                        BufferedImage displayVerticalFlipImage = ImageUtil.getHorizentalFlippedImageUp(displayedImage, g);
                        g.drawImage(displayVerticalFlipImage, 0, 0, null);
                        g.dispose();

                        horizentalFlipedUp = false;

                    } else if (verticalFlipedRight == true) {

                        // horizental flip :  Hight
                        BufferedImage displayHorizenatFlipImage = ImageUtil.getVerticalFlippedImageRight(displayedImage, g);
                        g.drawImage(displayHorizenatFlipImage, 0, 0, null);
                        g.dispose();

                        verticalFlipedRight = false;
                    } else if (verticalFlipedLeft == true) {

                        //horizental flip : Width
                        BufferedImage displayHorizenatlFlipImage = ImageUtil.getVerticalFlippedImageLeft(displayedImage, g);
                        g.drawImage(displayHorizenatlFlipImage, 0, 0, null);
                        g.dispose();

                        verticalFlipedLeft = false;

                    }

                }
            }

        };

        imagePanel.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent event) {

                if (displayedImage != null) {
                    System.out.println(".mouseMoved()" + "x:" + event.getX() + "y:" + event.getY());

                    Color color = new Color(displayedImage.getRGB(event.getX(), event.getX()));
                    System.out.println("RGB = " + color);

                    showRGB.setText("Pstion is x = " + event.getX() + " and y= " + event.getY() + " \n AND   RGB =" + color);

                    displayedImage.getWidth();
                    displayedImage.getHeight();
                }
            }
        });

        imagePanel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                System.out.println("Mouse Entered Image at : " + e.getX() + ": " + e.getY());

            }

            @Override
            public void mouseExited(MouseEvent e) {

                showRGB.setText("Mouse Image at : " + e.getX() + ": " + e.getY());
                System.out.println("Mouse Exit Image at : " + e.getX() + ": " + e.getY());
            }

        });
        imagePanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {

                // System.out.println("sourcefolder" + sourceFolder);
                int notches = e.getWheelRotation();

                if (notches < 0) {
                    System.out.println("Mouse wheel moved UP ");

                    nextImg = nextImg + 1;

                } else {
                    System.out.println("Mouse wheel moved Down ");

                    nextImg = nextImg - 1;
                    if (nextImg < 0) {

                        nextImg = files.size();
                    }
                }

                if (nextImg > files.size()) {
                    System.out.println("finished >>>>>>>>>>>>>>>>>>>>>files.size() =" + files.size() + "    and   nextImg" + nextImg);
                    nextImg = 1;

                } else {

                    imagePath = files.get(nextImg).toString();

                    System.out.println("path" + imagePath);
                    displayedImage = DicomUtil.getDicomDCM(imagePath);

                    if (displayedImage == null) {
                    } else {
                        imagePanel.setMinimumSize(new Dimension(displayedImage.getWidth(), displayedImage.getHeight()));
                        imagePanel.repaint();
                        imagePanel.revalidate();

                        jt.setTableFromDicomTags((ArrayList<DicomTags>) getListDicomHeader(imagePath));
                    }
                }
            }

        });

        jt = new JTablDicomTags();
        sp = new JScrollPane(jt);
        selectImgage = new JButton("Chooser");
        increaeRotaionAngel = new JButton("Incease");
        decreaeRotaionAngel = new JButton("Descreas");
        showRGB = new JLabel("Postion is :");
        monochrom = new JButton("Monochrom");
        filterBlure = new JButton("FilterBlure");
        horizentalFlippedDown = new JButton("horizentalFlip");
        horizentalFlippedUp = new JButton("horizentalFlip");
        verticalFlippedRight = new JButton("VerticalFlip");
        verticalFlippedLeft = new JButton("VerticalFlip");
        updateTagValue = new JButton("Update");
        newTagValueEditText = new JTextField();

        frame.add(container);
        container.add(imagePanel, "wrap");
        container.add(selectImgage, "split,wrap,sg 1");
        container.add(increaeRotaionAngel, "split,sg 1");
        container.add(decreaeRotaionAngel, "wrap,split,sg 1");
        container.add(monochrom, "split,sg 1");
        container.add(filterBlure, "split,wrap,sg 1");
        container.add(horizentalFlippedDown, "split,sg 1");
        container.add(horizentalFlippedUp, "split,wrap,sg 1");
        container.add(verticalFlippedRight, "split,sg 1");
        container.add(verticalFlippedLeft, "split,wrap,sg 1");

        //frame.add(showRGB);
        // imagePanel.setBounds(150, 150, 200, 200);
        imagePanel.setBackground(Color.black);
        // imagePanel.setLayout(new MigLayout());

        //It'll pass off painting the background to its parent, which may draw its own background.
        //imagePanel.setOpaque(false);
        // imagePanel.setLayout(null);
        frame.setTitle("Image");
        frame.setMinimumSize(new Dimension(800, 800));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new MigLayout());

        selectImgage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                souraceFolder = getImagePath();
//
//                //imagePath = getImagePath();
//                String[] array = souraceFolder.split("/");
//                String sourceFolder = "";
//                for (int i = 0; i < array.length - 1; i++) {
//
//                    sourceFolder += array[i] + "/";
//
//                }
                files = FileUtil.getFiles(souraceFolder);

                imagePath = files.get(nextImg).toString();
                //  displayedImage = ImageIO.read(new File(souraceFolder));

                displayedImage = DicomUtil.getDicomDCM(imagePath);
                imagePanel.setMinimumSize(new Dimension(displayedImage.getWidth(), displayedImage.getHeight()));
                imagePanel.repaint();
                imagePanel.revalidate();

                rotaionValue = 0;
                imagePanel.repaint();

                increaeRotaionAngel.setEnabled(true);
                decreaeRotaionAngel.setEnabled(true);
                monochrom.setEnabled(true);
                filterBlure.setEnabled(true);
                horizentalFlippedDown.setEnabled(true);
                horizentalFlippedUp.setEnabled(true);
                verticalFlippedRight.setEnabled(true);
                verticalFlippedLeft.setEnabled(true);

                container.add(sp);
                jt.setTableFromDicomTags((ArrayList<DicomTags>) getListDicomHeader(imagePath));
                container.add(newTagValueEditText, "sg 1");
                container.add(updateTagValue);
            }
        });

        increaeRotaionAngel.setBounds(10, 10, 100, 30);
        increaeRotaionAngel.setBackground(Color.green);
        increaeRotaionAngel.setEnabled(false);
        increaeRotaionAngel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {

                rotaionValue = rotaionValue + 5;
                imagePanel.repaint();
            }
        });

        decreaeRotaionAngel.setBounds(10, 50, 100, 30);
        decreaeRotaionAngel.setEnabled(false);
        decreaeRotaionAngel.setBackground(Color.green);
        decreaeRotaionAngel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                rotaionValue = rotaionValue - 5;
                imagePanel.repaint();
            }
        });

        monochrom.setBounds(10, 90, 100, 30);
        monochrom.setEnabled(false);
        monochrom.setBackground(Color.gray);
        monochrom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                monoChrom = true;
                imagePanel.repaint();
            }
        });

        filterBlure.setBounds(10, 130, 100, 30);
        filterBlure.setEnabled(false);
        filterBlure.setBackground(Color.gray);
        filterBlure.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                filterBlur = true;
                imagePanel.repaint();
            }
        });

        horizentalFlippedDown.setBounds(10, 170, 100, 30);
        horizentalFlippedDown.setEnabled(false);
        horizentalFlippedDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                horizentalFlipedDown = true;
                imagePanel.repaint();
            }
        });

        horizentalFlippedUp.setBounds(10, 200, 100, 30);
        horizentalFlippedUp.setEnabled(false);
        horizentalFlippedUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                horizentalFlipedUp = true;
                imagePanel.repaint();
            }
        });

        verticalFlippedRight.setBounds(10, 240, 100, 30);
        verticalFlippedRight.setEnabled(false);
        verticalFlippedRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                verticalFlipedRight = true;
                imagePanel.repaint();
            }
        });

        verticalFlippedLeft.setBounds(10, 270, 100, 30);
        verticalFlippedLeft.setEnabled(false);
        verticalFlippedLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                verticalFlipedLeft = true;
                imagePanel.repaint();
            }
        });

        updateTagValue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

//                Enumeration enu;
//                Hashtable ht = jt.tag;
//
//                enu = ht.keys();
//
//                String str;
//                while (enu.hasMoreElements()) {
//                    str = (String) enu.nextElement();
//                    System.out.println(str + ": " + ht.get(str));
//                }

                //System.out.println("new value" + jt.getSelectedDicomTags().getTagValue().toString());

                DicomUtil.UpdateSpecificTags(souraceFolder, jt.getSelectedDicomTags().getTag(), newTagValueEditText.getText());

                jt.setTableFromDicomTags((ArrayList<DicomTags>) getListDicomHeader(imagePath));

            }
        }
        );

        showRGB.setBounds(
                10, 600, 600, 100);

    }

    public static String getImagePath() {

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("/home/shady/(1)Optimal/(3)Files/DicomUn/3-1502789418500"));
        chooser.setDialogTitle("Select Directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            System.out.println("Image Path " + chooser.getSelectedFile().getAbsolutePath());
            return chooser.getSelectedFile().getAbsolutePath();

        }
        return null;

    }

    public static void main(String[] args) {

        new Image();
    }

}
