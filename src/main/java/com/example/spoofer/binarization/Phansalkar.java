package com.example.spoofer.binarization;

import java.awt.image.BufferedImage;

public class Phansalkar {

    private static int width;
    private static int height;
    public final int pixel_ob = 0xffffffff; //object pixel
    public final int pixel_bg = 0xff000000; //background pixel

    public static BufferedImage PhansalkarBinarization(BufferedImage imgBuff, int window, double k)
    {
        //Window size (for each side)

        width = imgBuff.getWidth();
        height = imgBuff.getHeight();
        double R = 0.0;

        BufferedImage img = DeepCopy.deepCopy(imgBuff);

        //Scanning the entire image
        for (int column = 0; column < width; column++)
        {
            for (int row = 0; row < height; row++)
            {
                int iAverage = 0;
                int iRed=0, iGreen=0, iBlue=0;

                //Sweeping the window to get the average
                for(int ji = -window ; ji < window ; ji++)
                {
                    for(int jj = -window ; jj < window ; jj++)
                    {
                        //Accumulating the values and then taking the average - do if serves to protect the window from not walking out of the image
                        if(column+ji >= 0 && column+ji < width)
                            if(row+jj >= 0 && row+jj < height)
                            {
                                iRed += imgBuff.getRGB(column+ji, row+jj) & 0xff0000 >> 16;
                                iGreen += imgBuff.getRGB(column+ji, row+jj) & 0x00ff00 >> 8;
                                iBlue += imgBuff.getRGB(column+ji, row+jj) & 0xff;
                                //iAverage += imgBuff.getRGB(column+ji, row+jj) & 0x00ff0000 >> 16;
                            }
                    }
                }
                iAverage = (iRed + iGreen + iBlue ) / 3;

                double area = (window*2) * (window*2);
                double standardDeviation = 0.0;
                double mean = iAverage/area;
                int num;


                for(int ji = -window ; ji < window ; ji++)
                {
                    for(int jj = -window ; jj < window ; jj++)
                    {
                        //Accumulating the values and then taking the average - do if serves to protect the window from not walking out of the image
                        if(column+ji >= 0 && column+ji < width)
                            if(row+jj >= 0 && row+jj < height)
                            {
                                iRed = imgBuff.getRGB(column+ji, row+jj) & 0xff0000 >> 16;
                                iGreen = imgBuff.getRGB(column+ji, row+jj) & 0x00ff00 >> 8;
                                iBlue = imgBuff.getRGB(column+ji, row+jj) & 0xff;
                                num = (iRed + iGreen + iBlue) / 3;

                                //R = Math.max(R, Math.pow(num - mean, 2));
                                standardDeviation += Math.pow(num - mean, 2);

                            }
                    }
                }

                double SD = Math.sqrt(standardDeviation/area);
                int pixelRed = img.getRGB(column, row) & 0xff0000 >> 16;
                int pixelGreen = img.getRGB(column, row) & 0x00ff00 >> 8;
                int pixelBlue = img.getRGB(column, row) & 0xff;
                int pixelAverage = (pixelRed + pixelGreen + pixelBlue) /3;
                double average = iAverage / area;
                R = Math.max(R, standardDeviation);
                //***********************************//

                //* constanst to implements fielsds *//
                double p = 3;
                double q = 10;

                //Phansalkar Pattern
                //double Phansalkar = average * (1 + k*(SD / R - 1));
                double Phansalkar = average *  (1 + p * Math.exp(-q * average) + (k * (SD / R - 1) ));


                //is higher than average
                //int pixel = img.getRGB(column, row) & 0x00ff0000 >> 16;
                if (pixelAverage > Phansalkar)
                    img.setRGB(column, row, 0x00FFFFFF );
                else
                    img.setRGB(column, row, 0x00000000 );
            }
        }

        return img;
    }
}
