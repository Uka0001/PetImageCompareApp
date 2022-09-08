package org.uka0001;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        GroupRegion gp = new GroupRegion();

        File file = new File("src/resources/actual.png");
        File file1 = new File("src/resources/expected.png");


        BufferedImage expectedImage = ImageIO.read(file);
        BufferedImage actualImage = ImageIO.read(file1);


        int[][] result = gp.imageCompression(actualImage, expectedImage);

        DrawImage dr = new DrawImage(result, expectedImage);

        dr.getResult();
    }
}
