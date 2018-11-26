/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.others;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author shady
 */
public class ImagesDifference {

    public static void main(String[] args) {

        try {
            test();
        } catch (IOException ex) {
            Logger.getLogger(ImagesDifference.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void test() throws IOException {
        ImageIO.write(
                getDifferenceImage(
                        ImageIO.read(new File("/home/shady/Desktop/DT/Test_Images/FA-redirect-mens-shoes-06-formal.jpg")),
                        ImageIO.read(new File("/home/shady/Desktop/DT/Test_Images/Screenshot from 2017-09-30 15-04-50.png"))),
                "png",
                new File("/home/shady/Desktop/DT/Test_Images/output.png"));

    }

    public static BufferedImage getDifferenceImage(BufferedImage img1, BufferedImage img2) {
        // convert images to pixel arrays...
        final int w = img1.getWidth(),
                h = img1.getHeight(),
                highlight = Color.MAGENTA.getRGB();
        final int[] p1 = img1.getRGB(0, 0, w, h, null, 0, w);
        final int[] p2 = img2.getRGB(0, 0, w, h, null, 0, w);
        // compare img1 to img2, pixel by pixel. If different, highlight img1's pixel...
        for (int i = 0; i < p1.length; i++) {
            if (p1[i] != p2[i]) {
                p1[i] = highlight;
            }
        }
        // save img1's pixels to a new BufferedImage, and return it...
        // (May require TYPE_INT_ARGB)
        final BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        out.setRGB(0, 0, w, h, p1, 0, w);
        return out;
    }

    static void test2() {

        BufferedImage img1 = null;
        BufferedImage img2 = null;

        try {
            URL url1 = new URL("http://rosettacode.org/mw/images/3/3c/Lenna50.jpg");
            URL url2 = new URL("http://rosettacode.org/mw/images/b/b6/Lenna100.jpg");
            img1 = ImageIO.read(url1);
            img2 = ImageIO.read(url2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width1 = img1.getWidth(null);
        int width2 = img2.getWidth(null);
        int height1 = img1.getHeight(null);
        int height2 = img2.getHeight(null);

        if ((width1 != width2) || (height1 != height2)) {
            System.err.println("Error: Images dimensions mismatch");
            System.exit(1);
        }

        long diff = 0;
        for (int i = 0; i < height1; i++) {
            for (int j = 0; j < width1; j++) {
                int rgb1 = img1.getRGB(j, i);
                int rgb2 = img2.getRGB(j, i);
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >> 8) & 0xff;
                int b1 = (rgb1) & 0xff;
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >> 8) & 0xff;
                int b2 = (rgb2) & 0xff;
                diff += Math.abs(r1 - r2);
                diff += Math.abs(g1 - g2);
                diff += Math.abs(b1 - b2);
            }
        }

    }
}
