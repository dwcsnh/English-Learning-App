package com.example.demo.Controller;

import com.example.demo.BasePlus.GoogleServices;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


import java.net.URL;
import java.util.ResourceBundle;

public class GoogleTranslateController implements Initializable {
    GoogleServices googleServices = new GoogleServices();
    @FXML
    private Button sourceEn;
    @FXML
    private Button sourceVi;
    @FXML
    private Button targetEn;
    @FXML
    private Button targetVi;
    @FXML
    private TextArea inputTextArea;
    @FXML
    private TextArea translationTextArea;

    @FXML
    public void updateLanguage(MouseEvent event) {
        if (event.getSource() == sourceEn || event.getSource() == targetVi) {
            googleServices.setSourceLanguage("en");
            googleServices.setTargetLanguage("vi");
            sourceEn.setOpacity(1);
            targetVi.setOpacity(1);
            sourceVi.setOpacity(0.5);
            targetEn.setOpacity(0.5);
        } else if (event.getSource() == sourceVi || event.getSource() == targetEn) {
            googleServices.setSourceLanguage("vi");
            googleServices.setTargetLanguage("en");
            sourceVi.setOpacity(1);
            targetEn.setOpacity(1);
            sourceEn.setOpacity(0.5);
            targetVi.setOpacity(0.5);
        }
    }

    @FXML
    public void speakSourceLanguage() {
        googleServices.pronounce(inputTextArea.getText(), googleServices.getSourceLanguage());
    }

    @FXML
    public void speakTargetLanguage() {
        googleServices.pronounce(translationTextArea.getText(), googleServices.getTargetLanguage());
    }

    @FXML
    public void translateText(KeyEvent event) {
        if (event.getSource() == inputTextArea) {
            if (event.getCode() == KeyCode.ENTER) {
                String input = inputTextArea.getText();
                String translation = googleServices.sentenceTranslation(input);
                translationTextArea.setText(translation);
            }
            if (inputTextArea.getText().isEmpty()) {
                translationTextArea.clear();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sourceVi.setOpacity(0.5);
        sourceEn.setOpacity(1);
        targetEn.setOpacity(0.5);
        targetVi.setOpacity(1);
        googleServices.setSourceLanguage("en");
        googleServices.setTargetLanguage("vi");
    }
}
