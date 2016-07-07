/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package histogramequilization;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author user
 */
public class ImageEquilization {
    
    public static void main(String[] args) throws IOException {

        BufferedImage img = null;
        img = ImageIO.read(new File("images\\test1.jpg"));

        HistEq histEq = new HistEq();                                           //call histogram equilization
        img = histEq.Change(img);                                               //retreive equilized image

        File f1 = new File("subhisto\\histotest1.png");                         //save the whole equalized image without subimaging
        ImageIO.write(img, "png", f1);
        
        int count = 1;                                                          //for naming convention as well as keeping track of the number of sub images.

        for (int i = 0; i <= (img.getHeight() - 19); i++) {                     //For a 24x24 Pixel image define height such that the last image to be extracted has atleast 24 Pixels to work with
            
            for (int j = 0; j <= (img.getWidth() - 19); j++) {                  //*For a 24x24 Pixel image define width such that the last image to be extracted has atleast 24 Pixels to work with
                
                BufferedImage sub = img.getSubimage(j, i, 19, 19);              //sub image starting point defined by i and j & 24x4 is the needed size for the sub image.             
                File f = new File("subimages\\" + count + ".png");              //save file. For Face detection it will not be saved but will be used in real time discarding non faces.
                ImageIO.write(sub, "png", f);
                
                System.out.println(count);                                      //Just to keep track of the image count.
                count++;
                
                //To normalize the equalization values between 0 and 1, file number 14185 is taken as it contains a face value for testing purpose
                if (count == 14185) {

                    HistoNormalize histoNormalize = new HistoNormalize();
                    histoNormalize.HistoNormal(sub);

                }
            }
        }
    }

}
