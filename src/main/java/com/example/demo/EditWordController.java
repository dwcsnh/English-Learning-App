package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class EditWordController {

    @FXML
    private Label currentWordLabel;

    @FXML
    private TextArea editTextArea;

    @FXML
    private WebView editWebView;

    Map<String, Word> mapStringWord;


    @FXML
    private void closeEditWord(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setCurrentWordLabel(String text) {
        if (text == null) {
            this.currentWordLabel.setText("Chưa chọn từ cần chuyển nghĩa!");
        }
        else {
            this.currentWordLabel.setText(text);
        }
    }

    public void setWebView(String content) {
        editWebView.getEngine().loadContent(content, "text/html");
    }

    public void setMapStringWord(Map<String, Word> mapStringWord) {
        this.mapStringWord = mapStringWord;
    }

    @FXML
    public void editUpdateDictionary(ActionEvent event) {
        Word newWord = mapStringWord.get(currentWordLabel.getText());
        newWord.setMeaning("<html>" + editTextArea.getText() + "<html>");
        mapStringWord.put(newWord.getSpelling(), newWord);
        closeEditWord(event);
    }
}
