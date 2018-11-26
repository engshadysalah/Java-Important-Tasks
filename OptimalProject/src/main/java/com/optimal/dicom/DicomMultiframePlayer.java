/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.dicom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReaderSpi;

/**
 *
 * @author shady
 */
public class DicomMultiframePlayer extends JFrame implements ActionListener, Runnable {

    //http://samucs.blogspot.com.eg/2010/01/dicom-multiframe-playback-using-dcm4che.html#more   
    // These Swing components will be our file chooser
    private JLabel fileLabel;
    private JTextField fileField;
    private JButton btnChoose;

    // These Swing components will be our playback controls
    private JButton btnPlay;
    private JButton btnPause;
    private JButton btnStop;
    private JButton btnExit;

    // This vector stores all image frames for playback 
    private Vector<BufferedImage> images;

    // This is a Swing JPanel control for image display 
    private ImagePanel imagePanel;

    // Used for simple playback control 
    private boolean stop;
    private int currentFrame;

    /**
     * Class constructor.
     */
    public DicomMultiframePlayer() {
        // TODO Auto-generated method stub   

        // Call the parent constructor and pass the window frame title.
        // Tell the application to terminate when the user close the window.
        // Configure a new BorderLyout for our application frame.
        super("XA Player using dcm4che - by samucs-dev");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());

        // Initialize our frame vector.
        // Create our panel to render Dicom images.
        images = new Vector<BufferedImage>();
        imagePanel = new ImagePanel();

        // Configure the file chooser component.
        fileLabel = new JLabel("File:");
        fileField = new JTextField(30);
        btnChoose = this.createJButton(25, 25, "...");

        // Configure our JButton components for playback control.
        btnPlay = this.createJButton(80, 25, "Play");
        btnPause = this.createJButton(80, 25, "Pause");
        btnStop = this.createJButton(80, 25, "Stop");
        btnExit = this.createJButton(80, 25, "Exit");
        btnPause.setEnabled(false);
        btnStop.setEnabled(false);

        // Add the file chooser to the top of the frame window.
        JPanel panelNorth = new JPanel();
        panelNorth.add(fileLabel);
        panelNorth.add(fileField);
        panelNorth.add(btnChoose);
        this.getContentPane().add(panelNorth, BorderLayout.NORTH);

        // Add the image render panel to the center of the frame window.
        this.getContentPane().add(imagePanel, BorderLayout.CENTER);

        // Add the buttons to the bottom of the frame window.
        JPanel panelSouth = new JPanel();
        panelSouth.add(btnPlay);
        panelSouth.add(btnPause);
        panelSouth.add(btnStop);
        panelSouth.add(btnExit);
        this.getContentPane().add(panelSouth, BorderLayout.SOUTH);

        // Configure frame window size and then show it.
        this.setSize(new Dimension(500, 500));
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    /**
     * This class implements the Runnable interface. It will handle thread
     * execution.
     */
    @Override
    public void run() {
        // TODO Auto-generated method stub  

        while (true) {
            if (!btnPlay.isEnabled()) {
                // Check if it has to stop. */
                if (stop) {
                    break;
                }
                // Increment the current frame and update the display image. */
                currentFrame++;
                if (currentFrame == images.size()) {
                    currentFrame = 0;
                }
                imagePanel.setImage(
                images.get(currentFrame));
                // Cause our thread to sleep 70 milliseconds. */
                // Increase this value to slow playback speed. */
                try {
                    Thread.sleep(70);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * This class implements the ActionListener interface. It will handle action
     * events performed by buttons.
     *
     * @param e ActionEvent object source.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub  
        // The user is opening a file. */
      
        if (e.getSource().equals(btnChoose)) {
            JFileChooser chooser = new JFileChooser();
             chooser.setCurrentDirectory(new java.io.File("/home/shady/(1)Optimal/(3)Files/test"));
            int action = chooser.showOpenDialog(this);
            switch (action) {
                case JFileChooser.APPROVE_OPTION:
                    this.openFile(chooser.getSelectedFile());
                    break;
                case JFileChooser.CANCEL_OPTION:
                    return;
            }
        }
        // Start playback of Dicom frames. */
        if (e.getSource().equals(btnPlay)) {
            btnPlay.setEnabled(false);
            btnPause.setEnabled(true);
            btnStop.setEnabled(true);
            stop = false;
            new Thread(this).start();
        }
        // Pause playback. */
        if (e.getSource().equals(btnPause)) {
            btnPlay.setEnabled(true);
            btnPause.setEnabled(false);
            btnStop.setEnabled(true);
            stop = false;
        }
        // Stop. Return to the first frame (0). */
        if (e.getSource().equals(btnStop)) {
            btnPlay.setEnabled(true);
            btnPause.setEnabled(false);
            btnStop.setEnabled(false);
            stop = true;
            currentFrame = 0;
            imagePanel.setImage(images.get(0));
        }
        // Exit the application. */
        if (e.getSource().equals(btnExit)) {
            System.exit(0);
        }
    
    }

    /**
     * This is a private class to render images onto a JPanel component.
     */
    class ImagePanel extends JPanel {
        // TODO Auto-generated method stub  

        private BufferedImage image;
        // Class constructor. */

        public ImagePanel() {
            super();
            this.setPreferredSize(new Dimension(1024, 1024));
            this.setBackground(Color.black);
        }

        // Set the image to be rendered. */
        public void setImage(BufferedImage image) {
            this.image = image;
            this.updateUI();
        }
        // Render the image. */

        @Override
        public void paint(Graphics g) {
            if (this.image != null) {
                g.drawImage(this.image, 0, 0, image.getWidth(), image.getHeight(), null);
            }
        }

    };

    /**
     * Run the program.
     *
     * @param args Program arguments.
     */
    public static void main(String[] args) {
        new DicomMultiframePlayer();
    }

    /**
     * Create a new Java Swing JButton.
     *
     * @param width button width.
     * @param height button height.
     * @param text button text.
     * @return a new JButton.
     */
    private JButton createJButton(int width, int height, String text) {
        JButton b = new JButton(text);
        b.setMinimumSize(new Dimension(width, height));
        b.setMaximumSize(new Dimension(width, height));
        b.setPreferredSize(new Dimension(width, height));

        // button action will be handled by 'this' class 
        b.addActionListener(this);
        return b;
    }

    private void openFile(File file) {
        // Clear the image vector */
        images.clear();
        try {
            // Here we get a new image reader for our Dicom file. */
            System.out.println("Reading DICOM image...");
            ImageReader reader = new DicomImageReaderSpi().createReaderInstance();
            FileImageInputStream input = new FileImageInputStream(file);
            reader.setInput(input);

            // Just to show the number of frames. */
            int numFrames = reader.getNumImages(true);
            System.out.println("DICOM image has " + numFrames + " frames...");

            // For each frame we read its image and then add it to the image vector. */
            System.out.println("Extracting frames...");
            for (int i = 0; i < numFrames; i++) {
                BufferedImage img = reader.read(i);
                images.add(img);
                System.out.println(" > Frame " + (i + 1));
            }
            // That's it we read all frames. */
            System.out.println("Finished.");
        } catch (Exception e) {
            e.printStackTrace();
            imagePanel.setImage(null);
            return;
        }
        // This is the first playback control. 
        // After reading we always display the first frame. */
        stop = false;
        currentFrame = 0;
        imagePanel.setImage(images.get(0));
    }

}
