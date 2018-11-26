/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.util;

import com.optimal.dicom.ReadDicomTags;
import com.optimal.dicom.ShowDicom;
import ij.IJ;
import ij.plugin.DICOM;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che3.data.Tag;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReaderSpi;

/**
 *
 * @author root
 */
public class ImageUtil {

    public static BufferedImage getplayRotatedImage(BufferedImage displayedImage, Graphics graph, int rotaionValue) {

        ((Graphics2D) graph).rotate(Math.toRadians(rotaionValue), displayedImage.getWidth() / 2, displayedImage.getHeight() / 2);

        return displayedImage;
    }

    public static BufferedImage getFilteredBlureImage(BufferedImage displayedImage, int filterWidth, int filterHight) {

        // detrmiend (matrix = typ) of filter 
        float[] matrix = new float[filterWidth * filterHight];
        for (int i = 0; i < filterWidth * filterHight; i++) {
            matrix[i] = 1.0f / 500.0f;
        }

        BufferedImage dest = new BufferedImage(displayedImage.getWidth(), displayedImage.getHeight(), displayedImage.getType());
        BufferedImage filteredIamge = new ConvolveOp(new Kernel(filterWidth, filterHight, matrix), ConvolveOp.EDGE_ZERO_FILL, null).filter(displayedImage, dest);

        // to save filteredIamge as image.png
//           File outputfilteredImage = new File("/home/shady/(3) Downloads/Images/filterImage.png");
//           ImageIO.write(filteredIamge, "png", outputfilteredImage);
        return filteredIamge;

    }

    // drawing from up to down  (Resutl Image is VerticalFlippedImage)
    public static BufferedImage getHorizentalFlippedImageDown(BufferedImage displayedImage, Graphics graph) {

        AffineTransform at = new AffineTransform();

        at.concatenate(AffineTransform.getScaleInstance(1, -1));

        at.concatenate(AffineTransform.getTranslateInstance(0, -displayedImage.getHeight()));

        ((Graphics2D) graph).transform(at);

        return displayedImage;

    }

    // drawing from down to up  (Resutl Image likes  Main Image)
    public static BufferedImage getHorizentalFlippedImageUp(BufferedImage displayedImage, Graphics graph) {

        AffineTransform at = new AffineTransform();

        at.concatenate(AffineTransform.getScaleInstance(-1, 1));

        at.concatenate(AffineTransform.getTranslateInstance(0, displayedImage.getHeight()));

        ((Graphics2D) graph).transform(at);

        return displayedImage;

    }

    // drawing from left to right (Result Image is  HorizenatlFlippedImage)
    public static BufferedImage getVerticalFlippedImageLeft(BufferedImage displayedImage, Graphics graph) {

        AffineTransform at = new AffineTransform();

        at.concatenate(AffineTransform.getScaleInstance(-1, 1));

        at.concatenate(AffineTransform.getTranslateInstance(-displayedImage.getWidth(), 0));

        ((Graphics2D) graph).transform(at);

        return displayedImage;

    }

    // drawing from right to left (Resutl Image likes  Main Image)
    public static BufferedImage getVerticalFlippedImageRight(BufferedImage displayedImage, Graphics graph) {

        AffineTransform at = new AffineTransform();

        at.concatenate(AffineTransform.getScaleInstance(1, -1));

        at.concatenate(AffineTransform.getTranslateInstance(displayedImage.getWidth(), 0));

        ((Graphics2D) graph).transform(at);

        return displayedImage;

    }

    public static BufferedImage getplayMonoChoromImage(BufferedImage displayedImage) {

        for (int y = 0; y < displayedImage.getWidth(); y++) {
            for (int i = 0; i < displayedImage.getHeight(); i++) {

                Color color = new Color(displayedImage.getRGB(y, i));

                System.out.println("int" + color);

                if (127 <= color.getRed() || 127 <= color.getGreen() || 127 <= color.getBlue()) {
                    displayedImage.setRGB(y, i, Color.WHITE.getRGB());
                } else {
                    displayedImage.setRGB(y, i, Color.BLACK.getRGB());

                }

            }

        }
        return displayedImage;
    }

    public static BufferedImage getGreenImage(BufferedImage displayedImage) {

        for (int y = 0; y < displayedImage.getHeight(); y++) {
            for (int x = 0; x < displayedImage.getWidth(); x++) {

                int p = displayedImage.getRGB(x, y);

                int a = (p >> 24) & 0xff;

                int g = (p >> 8) & 0xff;

                //calculate average
                //replace RGB value with avg
                p = (a << 24) | (0 << 16) | (g << 8) | 0;

                displayedImage.setRGB(x, y, p);

            }

        }

        return displayedImage;
    }

}
