package com.example.spoofer.binarization;

import java.awt.image.BufferedImage;

public class GlobalThresholding {

    public static BufferedImage simpleBinarization(BufferedImage imgSrc, double threshold) {

        int width = imgSrc.getWidth();
        int height = imgSrc.getHeight();
        int[][] result = new int[width][height];

        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                result[row][col] = imgSrc.getRGB(row, col);

                int iRet = result[row][col];
                int iB = (iRet & 0xff);
                int iG = (( iRet & 0x00ff00) >> 8);
                int iR = (( iRet & 0xff0000) >> 16);
                int iAve = ( iR + iG + iB ) / 3;

                imgSrc.setRGB(row, col, (iR < threshold)?0:0xffffff);
            }
        }

        return imgSrc;
    }

}
