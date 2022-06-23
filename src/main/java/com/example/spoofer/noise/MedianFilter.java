package com.example.spoofer.noise;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MedianFilter {

    public static BufferedImage median(BufferedImage img, int window) {

        BufferedImage medianImage = new BufferedImage(
                img.getWidth(),
                img.getHeight(),
                img.getType());

        int width = medianImage.getWidth();
        int height = medianImage.getHeight();

        int rgb;
        List<Color> pixels = new ArrayList<>();
        int[] R;
        int[] G;
        int[] B;


        for(int column=1; column < width-1; column++)
            for(int row=1; row<height-1; row++)
            {
                for (int ji = -window; ji < window; ji++) {
                    for (int jj = -window; jj < window; jj++) {
                        if (column + ji >= 0 && column + ji < width) {
                            if (row + jj >= 0 && row + jj < height) {
                                rgb = img.getRGB(column + ji, row + jj);
                                pixels.add(new Color(rgb));
                            }
                        }
                    }
                }


                R = new int[pixels.size()];
                B = new int[pixels.size()];
                G = new int[pixels.size()];

                for(int k = 0;k<pixels.size();k++){
                    R[k] = pixels.get(k).getRed();
                    B[k] = pixels.get(k).getBlue();
                    G[k] = pixels.get(k).getGreen();
                }
                Arrays.sort(R);
                Arrays.sort(G);
                Arrays.sort(B);
                int median = pixels.size()/2;
                medianImage.setRGB(column,row,new Color(R[median],G[median],B[median]).getRGB());
                pixels.clear();

            }


        return medianImage;
    }

}
