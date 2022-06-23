package com.example.spoofer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button loadImageBtn;
    @FXML
    private Button saveFingerprintBtn;

    @FXML
    private TextField outputFileName;

    @FXML
    private ImageView imageView;


    private BufferedImage originalImage;
    private String outputFileNameStr;

    @FXML
    protected void onLoadImageBtnClick() {
        Stage thisStage = (Stage) loadImageBtn.getScene().getWindow();
        originalImage = FileHandler.LoadImage(thisStage);
        imageView.setImage(FileHandler.convertToFxImage(originalImage));
    }

    @FXML
    protected void onSaveFingerprintBtnClick() {
        BufferedImage outputImage = Spoofer.spoof(originalImage);
        outputFileNameStr = outputFileName.getText();
        try {
            FileHandler.SaveImage(outputImage, outputFileNameStr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onChangeFileNameTextField() {

    }
}