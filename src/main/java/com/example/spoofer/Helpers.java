package com.example.spoofer;

import java.awt.image.BufferedImage;

public class Helpers {

    public static int[][] convertBinarizatedImgToArray2D(BufferedImage imgSrc) {

        int width = imgSrc.getWidth();
        int height = imgSrc.getHeight();
        int[][] imgArray = new int[width][height];

        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                imgArray[row][col] = imgSrc.getRGB(row, col) != -1 ? 1 : 0;
            }
        }

        return imgArray;
    }

    public static BufferedImage convertArraytoBinarizatedImg(int[][] imgArray){

        int width = imgArray[1].length;
        int height = imgArray.length;

        BufferedImage image = new BufferedImage(height - 2, width - 2, BufferedImage.TYPE_INT_RGB);

        for(int i = 1; i < height - 1; i++){
            for(int j = 1; j < width - 1; j++){

                if(imgArray[i][j] == 1) {
                    image.setRGB(i - 1, j - 1, 0);
                }
                else {
                    image.setRGB(i - 1, j - 1, 0xffffff);
                }
            }
        }
        return image;
    }

    public void print2Darray(int [][] array)
    {
        int numberOfRows = array.length;
        int numberOfColumns = array[0].length;

        for(int row = 0; row < numberOfRows - 1; row++)
        {
            for(int col = 0; col < numberOfColumns - 1; col++)
            {

                if (array[row][col] != 0) {
                    System.out.print(array[row][col]);
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public void printArray(int [] array) {
        System.out.print('[');
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ", ");
        }
        System.out.print("]\n");
    }



}
