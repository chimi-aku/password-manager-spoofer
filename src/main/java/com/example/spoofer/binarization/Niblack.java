package com.example.spoofer.binarization;

import java.awt.image.BufferedImage;

public class Niblack{

    private static int width;
    private static int height;
    public final int pixel_ob = 0xffffffff; //object pixel
    public final int pixel_bg = 0xff000000; //background pixel

    public static BufferedImage NiblackBinarization(BufferedImage imgSrc, int window, double k){
        BufferedImage img = DeepCopy.deepCopy(imgSrc);

        width = img.getWidth();
        height = img.getHeight();

        int iB, iG, iR, rgb, sum, num;
        double area, mean, standardDeviation, average;

        int pixelRgb, pixelR, pixelG, pixelB, pixelA;
        double NiBlack;


        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {

                iR = iG = iB = 0;
                for(int ji = -window; ji < window; ji++){
                    for(int jj = -window; jj < window; jj++){
                        if((column + ji >= 0 && column + ji < width)&&(row + jj >= 0 && row + jj < height)){
                            rgb = img.getRGB(column + ji, row + jj);
                            iR += rgb & 0xff0000 >> 16;
                            iG += rgb & 0x00ff00 >> 8;
                            iB += rgb & 0xff;
                        }
                    }
                }

                sum = (iR + iG + iB) / 3;
                area = (window)*(window)*4;
                standardDeviation = 0.0;
                mean = sum/area;

                for(int ji = -window; ji < window; ji++){
                    for(int jj = -window; jj < window; jj++){
                        if(column + ji >= 0 && column + ji < width){
                            if(row + jj >= 0 && row + jj < height){
                                rgb = img.getRGB(column + ji, row + jj);
                                iR = rgb & 0xff0000 >> 16;
                                iG = rgb & 0x00ff00 >> 8;
                                iB = rgb & 0xff;
                                num = (iR + iG + iB) / 3;
                                standardDeviation += Math.pow(num - mean, 2);
                            }
                        }
                    }
                }
                standardDeviation = Math.sqrt(standardDeviation/area);
                pixelRgb = img.getRGB(column, row);
                pixelR = pixelRgb & 0xff0000 >> 16;
                pixelG = pixelRgb & 0x00ff00 >> 8;
                pixelB = pixelRgb & 0xff;
                pixelA = (pixelR + pixelG + pixelB) / 3;
                average = sum / area;

                NiBlack = average + k * standardDeviation;

                if(pixelA > NiBlack)
                    img.setRGB(column, row, 0xffffffff);
                else
                    img.setRGB(column, row, 0xff000000);
            }
        }
        return img;
    }
}
