/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package histogramequilization;

import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author user
 */
public class HistoNormalize {

    public void HistoNormal(BufferedImage bi) throws IOException {

        int numBands = bi.getRaster().getNumBands();
        int[] iarray = new int[numBands];
        String content = "";
        int count = 0;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {

                float value = bi.getRaster().getPixel(i, j, iarray)[0];

                value = value / 255;
                content += Float.toString(value) + " ";
                //System.out.println(value);
                FileWriter writer = new FileWriter("subImageHistValues\\file1.txt");
                writer.write(content);
                writer.close();
                count++;
            }
        }
        System.out.println(count);
    }
}
