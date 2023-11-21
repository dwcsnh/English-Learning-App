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
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class EditWordController {

    @FXML
    private Label currentWordLabel;

    @FXML
    private TextArea editTextArea;

    @FXML
    private WebView editWebView;

    private boolean unselectedWordOpen = false;
    ContainerController parent = new ContainerController();


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

//    public void setMapStringWord(Map<String, Word> mapStringWord) {
//        this.mapStringWord = mapStringWord;
//    }

    @FXML
    public void editUpdateDictionary(ActionEvent event) {
        Word newWord = parent.getDictionaryManagement().getMapStringWord().get(currentWordLabel.getText());
        String newMeaning = "<html><i>" + newWord.getSpelling() +"</i><br/><ul><li><font color='#cc0000'><b> "
                + editTextArea.getText() + "</b></font></li></ul></html>";
        newWord.setMeaning(newMeaning);
        System.out.println(newMeaning);
        parent.getDictionaryManagement().editDefinition(newWord, newMeaning);
        parent.getFavorite().editDefinition(newWord, newMeaning);
        parent.getHistory().editDefinition(newWord, newMeaning);
        parent.getDictionaryManagement().getMapStringWord().put(newWord.getSpelling(), newWord);
        parent.getDictionaryManagement().writeToFile(parent.getDictionaryManagement().getDictionary().getWordList());
        parent.getHistory().writeToFile(parent.getHistory().getDictionary().getWordList());
        parent.getFavorite().writeToFile(parent.getFavorite().getDictionary().getWordList());
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
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/demo/logo/B.png")));
            stage.getIcons().add(icon);
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

    public void sync(ContainerController parent) {
        this.parent = parent;
    }
}