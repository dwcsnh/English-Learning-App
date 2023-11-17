package com.example.demo.Controller;

import com.example.demo.BasePlus.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class EditWordController {

    @FXML
    private Label currentWordLabel;

    @FXML
    private TextArea editTextArea;

    @FXML
    private WebView editWebView;

    Map<String, Word> mapStringWord;

    private boolean unselectedWordOpen = false;


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
        newWord.setMeaning("<html>" + editTextArea.getText() + "</html>");
        mapStringWord.put(newWord.getSpelling(), newWord);
        closeEditWord(event);
    }

    @FXML
    public void showUnselectedWord() {
        if (unselectedWordOpen) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/fxml/unselectedWord.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Unselected Word");
            stage.setScene(new Scene(root));
            stage.setOnHidden(event -> unselectedWordOpen = false);
            unselectedWordOpen = true;
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeUnselectedWord(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
