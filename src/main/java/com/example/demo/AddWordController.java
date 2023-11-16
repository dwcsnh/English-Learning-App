package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class AddWordController {
    private String target;
    private String definition;
    DictionaryManagement dictionaryManagement = new DictionaryManagement("data\\favorite.txt");
    ArrayList<Word> words = dictionaryManagement.getDictionary().getWordList();
    @FXML
    private TextField targetTextField;
    @FXML
    private TextArea definitionTextArea;

    public AddWordController() throws IOException {
    }

    @FXML
    public void getTargetOnTextField() {
        System.out.println(targetTextField.getText());
    }

    @FXML
    public void getDefinationOnTextArea() {
        System.out.println(definitionTextArea.getText());
    }

    @FXML
    public void saveWord() {
        target = targetTextField.getText();
        System.out.println(targetTextField.getText());
        definition = definitionTextArea.getText();
        System.out.println(definitionTextArea.getText());
        if (wordExist() != -1) {
            setAlertOnWordExist();
        } else {
            addWordToFile();
            setAlertOnSuccessAddWord();
        }

    }

    public int wordExist() {
        return dictionaryManagement.getDictionary().findWordIndex(0, words.size() - 1, target);
    }

    public void addWordToFile() {
        String editDef = "<html><i>-" + target +"</i><br/><ul><li><font color='#cc0000'><b> "
                        + definition + "</b></font></li></ul></html>";
        Word word = new Word(target, editDef);
        dictionaryManagement.getDictionary().addWordByBinary(word);
        dictionaryManagement.writeToFile(words);
    }


    public void setAlertOnWordExist() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText("This word already exists!");
        alert.showAndWait();
    }

    public void setAlertOnSuccessAddWord() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText("Add this word success!");
        alert.showAndWait();
    }
}
