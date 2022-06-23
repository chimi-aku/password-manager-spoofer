package com.example.spoofer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button loadImageBtn;

    @FXML
    private TextField outputFilenName;

    private BufferedImage originalImage;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onLoadImageBtnClick() {
        Stage thisStage = (Stage) loadImageBtn.getScene().getWindow();
        originalImage = FileHandler.LoadImage(thisStage);

    }
}