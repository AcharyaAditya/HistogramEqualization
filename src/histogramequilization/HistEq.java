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
        
        DrawGraph drawGraph = new DrawGraph(histogram,255, anzpixel);
//        drawGraph.createAndShowGui(histogram, 255);
        
        int indexNo = 0;
        int minFrequencyCount = anzpixel;
        for (int k = 0; k < 255 ; k++){
            if(histogram[k] < minFrequencyCount && histogram[k] != 0){
                minFrequencyCount = histogram[k];
                indexNo = k;
            }
        }
        
        System.out.println(minFrequencyCount + " " +indexNo);

        // build a Lookup table LUT containing scale factor
        int sum = 0;
        float[] lut = new float[anzpixel];
        for (i = 0; i < 255; ++i) {
            sum += histogram[i];
//            lut[i] = sum * 255 / anzpixel;
            lut[i] = (sum-minFrequencyCount) * 255/(anzpixel - minFrequencyCount);
//            lut[i] = ((sum - minFrequencyCount)/(anzpixel - minFrequencyCount)) * 255;
        }

        // transform image using sum histogram as a Lookup table

        for (int x = 1; x < width; x++) {
            for (int y = 1; y < height; y++) {
                int valueBefore = bi.getRaster().getPixel(x, y, iarray)[0];
                int valueAfter = (int) lut[valueBefore];
                iarray[0] = valueAfter;
                iarray[1] = valueAfter;
                iarray[2] = valueAfter;
                bi.getRaster().setPixel(x, y, iarray);

            }
        }
        
        for (int x = 1; x < width; x++) {
            for (int y = 1; y < height; y++) {
                int valueBefore = bi.getRaster().getPixel(x, y, iarray)[0];
                histogram[valueBefore]++;
            }
        }
        
        DrawGraph drawGraph1 = new DrawGraph(histogram,255, anzpixel);
        return bi; //return buffered image to main
    }
}
