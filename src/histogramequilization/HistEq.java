/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package histogramequilization;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author user
 */
public class HistEq {

    public BufferedImage Change(BufferedImage bi) {

        //all required parameters declaration
        int width = bi.getWidth();
        int height = bi.getHeight();
        int anzpixel = width * height;
        int[] histogram = new int[255];  //to store intensity values from supplied image
        //int[] iarray = new int[1];
        int i = 0;

        //RBG2GRAY conversion
        for (i = 0; i < height; i++) {

            for (int j = 0; j < width; j++) {

                Color c = new Color(bi.getRGB(j, i));

                int red = (int) (c.getRed() * 0.299);
                int green = (int) (c.getGreen() * 0.587);
                int blue = (int) (c.getBlue() * 0.114);

                Color newColor = new Color(red + green + blue,
                        red + green + blue, red + green + blue);

                bi.setRGB(j, i, newColor.getRGB());  //set GRAY values to the image
            }
        }

        //HISTOGRAM EQUILIZATION SECTION
        int numBands = bi.getRaster().getNumBands();
        System.out.print(numBands);
        System.out.println("");
        int[] iarray = new int[numBands];

        //read pixel intensities into histogram
        for (int x = 1; x < width; x++) {
            for (int y = 1; y < height; y++) {
                int valueBefore = bi.getRaster().getPixel(x, y, iarray)[0];
                histogram[valueBefore]++;
            }
        }

        // build a Lookup table LUT containing scale factor
        int sum = 0;
        float[] lut = new float[anzpixel];
        for (i = 0; i < 255; ++i) {
            sum += histogram[i];
            lut[i] = sum * 255 / anzpixel;
        }

        // transform image using sum histogram as a Lookup table
        i = 0;
        for (int x = 1; x < width; x++) {
            for (int y = 1; y < height; y++) {
                int valueBefore = bi.getRaster().getPixel(x, y, iarray)[0];
                int valueAfter = (int) lut[valueBefore];
                iarray[0] = valueAfter;
                bi.getRaster().setPixel(x, y, iarray);
                i = i + 1;
            }
        }
        return bi; //return buffered image to main
    }
}
