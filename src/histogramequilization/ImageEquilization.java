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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        BufferedImage img = null, img1 = null;
        img = ImageIO.read(new File("images\\budo.jpg"));

//        File f = new File("subimages\\histobudo.png");
//        HistEq histEq = new HistEq();
//        //retreive equilized image
//        img1 = histEq.Change(img);
//                
//        ImageIO.write(img1, "png", f);
//        
        int count = 1; //for naming convention as well as keeping track of the number of sub images.

        //For a 24x24 Pixel image define height such that the last image to be extracted
        // has atleast 24 Pixels to work with
        for (int i = 0; i <= (img.getHeight() - 24); i++) {
            //*For a 24x24 Pixel image define width such that the last image to be extracted
            // has atleast 24 Pixels to work with
            for (int j = 0; j <= (img.getWidth() - 24); j++) {

                //sub image starting point defined by i and j & 24x4 is the needed size for the sub image.
                BufferedImage sub = img.getSubimage(j, i, 24, 24);

                //save file. For Face detection it will not be saved but will be used in real time discarding non faces.
                File f = new File("subimages\\" + count + ".png");

                //call histogram equilization 
                HistEq histEq = new HistEq();

                //retreive equilized image
                img1 = histEq.Change(sub);

                ImageIO.write(img1, "png", f);

                //Just to keep track of the image count.
                System.out.println(count);
                count++;
            }
        }
    }

}
