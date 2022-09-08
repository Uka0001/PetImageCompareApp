package org.uka0001;

import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.util.Map;

@Setter
@Getter
public class GroupRegion {
    private Map<Integer, Integer> coordinate;
    private long countDifferentPixels;
    private int[][] differentMatrix;
    private int regionCount = 2;
    private double avgDifference;
    private double percentDifferent = 0;

    public int [][] imageCompression(BufferedImage actualImage, BufferedImage expectedImage) {

        int widthA = actualImage.getWidth();
        int heightA = actualImage.getHeight();

        differentMatrix = new int[widthA][heightA];

        avgDifference = getDifference(widthA, heightA, expectedImage, actualImage);

        countDifferentPixels = getCountDiffPixels(expectedImage, actualImage, widthA, heightA, avgDifference, differentMatrix);

        setDifferentMatrix(differentMatrix);
        if (countDifferentPixels == 0) {
            System.out.println("photos are the same");
        } else {
            groupRegions();
        }


        return differentMatrix;
    }

    private long getCountDiffPixels(BufferedImage expectedImage, BufferedImage actualImage, int widthA, int heightA, double avgDifference, int[][] differentMatrix) {
        long counter = 0;
        for (int y = 0; y < heightA; y++) {
            for (int x = 0; x < widthA; x++) {
                if (differentPixels(expectedImage.getRGB(x, y), actualImage.getRGB(x, y), avgDifference)) {
                    differentMatrix[x][y] = 1;
                    counter++;
                }
            }
        }
        return counter;
    }




    public double getDifference(int widthA, int heightA, BufferedImage expectedImage, BufferedImage actualImage) {
        long difference = 0;
        for (int x = 0; x < widthA; x++) {
            for (int y = 0; y < heightA; y++) {
                difference += pixelDiff(expectedImage.getRGB(x, y), actualImage.getRGB(x, y));
            }
        }

        double total_pixels = widthA * heightA * 3;
        double avg_different_pixels = difference / total_pixels;
        return (avg_different_pixels / 255) * 100;

    }

    public int pixelDiff(int rgb1, int rgb2) {
        int r1 = (rgb1 >> 16) & 0xff;
        int g1 = (rgb1 >> 8) & 0xff;
        int b1 = rgb1 & 0xff;
        int r2 = (rgb2 >> 16) & 0xff;
        int g2 = (rgb2 >> 8) & 0xff;
        int b2 = rgb2 & 0xff;
        return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
    }

    private boolean differentPixels(int expectedRgb, int actualRgb, double avg) {
        if (expectedRgb == actualRgb) {
            return false;
        }

        int difference = pixelDiff(expectedRgb, actualRgb);
        long percentageDif = (difference / 255) * 100;

        return percentageDif > avg + ((avg * 100) * percentDifferent);
    }


    private boolean isJumpRejected(int x, int y) {
        return y < 0 || y >= differentMatrix.length || x < 0 || x >= differentMatrix[y].length || differentMatrix[y][x] != 1;
    }

    private void joinToRegion(int x, int y) {
        if (isJumpRejected(x, y)) {
            return;
        }

        differentMatrix[y][x] = regionCount;

        for (int i = 0; i < 5; i++) {
            joinToRegion(x + 1 + i, y);
            joinToRegion(x, y + 1 + i);

            joinToRegion(x + 1 + i, y - 1 - i);
            joinToRegion(x - 1 - i, y + 1 + i);
            joinToRegion(x + 1 + i, y + 1 + i);
        }
    }

    public void groupRegions() {
        for (int y = 0; y < differentMatrix.length; y++) {
            for (int x = 0; x < differentMatrix[y].length; x++) {
                if (differentMatrix[y][x] == 1) {
                    joinToRegion(x, y);
                    regionCount++;
                }
            }
        }
    }
}
