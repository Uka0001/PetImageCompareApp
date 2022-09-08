package org.uka0001;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DrawImage {
    public static final File OUTPUT = new File("src/resources/result.png");
    private final int[][] matrix;
    BufferedImage resultImage;
    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;
    private int countGroup;


    public DrawImage(int[][] matrix, BufferedImage resultImage) {
        this.matrix = matrix;
        this.resultImage = resultImage;
    }


    public void getResult() throws IOException {
        List<Point> coordinate = new ArrayList<>();
        countGroup = getMaxGroupValue();

        while (countGroup != 1) {
            coordinate = getCoordinate(countGroup);
            xStart = findXStart(coordinate);
            yStart = findYStart(coordinate);
            xEnd = findXEnd(coordinate);
            yEnd = findYEnd(coordinate);


            drawResult(xStart,yStart,xEnd,yEnd,resultImage);
            countGroup--;

        }


    }

    private int findYEnd(List<Point> coordinate) {
        return coordinate.stream().map(Point::getY).max(Double::compare).get().intValue();
    }

    private int findXEnd(List<Point> coordinate) {
        return coordinate.stream().map(Point::getX).max(Double::compare).get().intValue();
    }

    private int findYStart(List<Point> coordinate) {
        return coordinate.stream().map(Point::getY).sorted().findFirst().get().intValue();
    }

    private int findXStart(List<Point> coordinate) {
        return coordinate.stream().map(Point::getX).sorted().findFirst().get().intValue();
    }

    private List<Point> getCoordinate(int countGroup) {
        List<Point> list = new ArrayList<>();
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length;x++) {
                if (countGroup == matrix[y][x]) {
                    list.add(new Point(y,x));
                }
            }
        }
        return list;


    }

    public int getMaxGroupValue() {
        int value = 0;
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[x].length; y++) {
                if (value <= matrix[x][y]) {
                    value = matrix[x][y];
                }
            }
        }
        return value;
    }

    private void drawResult(int  xStart, int yStart, int xEnd, int yEnd, BufferedImage image) throws IOException {
        Graphics g2d = image.getGraphics();
        g2d.setColor(Color.RED);
        g2d.drawRect(xStart, yStart, xEnd-xStart, yEnd-yStart);
        File output = new File("src/resources/result.png");
        ImageIO.write(image, "png", output);
    }
}

