package org.uka0001.oracleExamples;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;

//Following java, example reads the contents of every
//pixel of an image and writes the RGB values to a file

public class GetPixels {
    public static void main(String args[]) throws Exception {
        FileWriter writer = new FileWriter("src/resources/pixel_values.txt");
        //Reading the image
        File file = new File("src/resources/actual.png");
        BufferedImage img = ImageIO.read(file);
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                //Retrieving contents of a pixel
                int pixel = img.getRGB(x, y);
                //Creating a Color object from pixel value
                Color color = new Color(pixel, true);
                //Retrieving the R G B values
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                writer.append(red + ":");
                writer.append(green + ":");
                writer.append(blue + "");
                writer.append("\n");
                writer.flush();
            }
        }
        writer.close();
        System.out.println("RGB values at each pixel are stored in the specified file");
    }
}
