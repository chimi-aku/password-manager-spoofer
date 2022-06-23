package com.example.spoofer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class FileHandler {
    private static BufferedImage img;


    public static BufferedImage LoadImage(Stage stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));

        File file = fileChooser.showOpenDialog(stage);

        try {
            img = ImageIO.read(file);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return img;
    }

    public static void SaveImage(BufferedImage img, String filename) throws IOException {
        File outputfile = new File(filename + ".jpg");
        ImageIO.write(img, "jpg", outputfile);
    }

    public static Image convertToFxImage(BufferedImage image) {
        // convert Buffered Img to Image

        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }
        return new ImageView(wr).getImage();
    }

    @SneakyThrows
    public void save2DArrayToTXT(int [][]array, String path) {

        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < array.length; i++)//for each row
        {
            for(int j = 0; j < array[0].length; j++)//for each column
            {
                builder.append(array[i][j]+"");//append to the output string
            }
            builder.append("\n");//append new line at the end of the row
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(builder.toString());//save the string representation of the board
        writer.close();

    }


    public int [][] loadFrom2DArray(String path) throws FileNotFoundException {

        int rows = 0;
        int cols = 0;
        Scanner file = null;


        try {
            file = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Getting number of rows and coums of array
        while (file.hasNextLine()) {
            String line = file.nextLine();
            cols = Math.max(cols, line.length());
            rows++;
        }

        int[][] resArr = new int[rows][cols];

        // Reseting scanner
        try {
            file = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Fillig up the array
        for(int row = 0; row < rows; row++) {
            String line = file.nextLine();
            for (int col = 0; col < cols; col++) {
                resArr[row][col] = Integer.parseInt(String.valueOf(line.charAt(col)));
            }
        }

        return resArr;

    }

}
