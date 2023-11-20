package com.example.demo.Controller;

import com.example.demo.BasePlus.DictionaryManagement;
import com.example.demo.BasePlus.Word;
import com.example.demo.Controller.DictionaryController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class AddWordController {
    private String target;
    private String definition;

    ContainerController parent = new ContainerController();
    DictionaryManagement dictionaryManagement = parent.getDictionaryManagement();
    ArrayList<Word> words = dictionaryManagement.getDictionary().getWordList();
    @FXML
    private TextField targetTextField;
    @FXML
    private TextArea definitionTextArea;
    boolean isAdded = false;

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
        System.out.println("t = " + targetTextField.getText());
        definition = definitionTextArea.getText();
        System.out.println(definitionTextArea.getText());
        if (target.isEmpty()) {
            creatAlert("Thông báo", "Vui lòng nhập từ cần thêm!");
        } else if(definition.isEmpty()) {
            creatAlert("Thông báo", "vui lòng nhập nghĩa của từ!");
        } else {
            if (wordExist() != -1) {
                setAlertOnWordExist();
            } else {
                addWordToFile();
                targetTextField.clear();
                definitionTextArea.clear();
                setAlertOnSuccessAddWord();
            }
        }
    }

    @FXML
    public void cancel() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText("You will lose data.");
        alert.setContentText("Do you want to cancel?");

        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes) {
            targetTextField.clear();
            definitionTextArea.clear();
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

    public int wordExist() {
        return dictionaryManagement.getDictionary().findWordIndex(0, words.size() - 1, target);
    }

    public void addWordToFile() {
        String editDef = "<html><i>" + target +"</i><br/><ul><li><font color='#cc0000'><b> "
                + definition + "</b></font></li></ul></html>";
        Word word = new Word(target, editDef);
        dictionaryManagement.getDictionary().addWordByBinary(word);
        words = dictionaryManagement.getDictionary().getWordList();
        System.out.println("..");
        for(Word x : words) {
            System.out.println(x.getSpelling());
        }
        System.out.println("..");
        //dictionaryController.setWords(words);
        dictionaryManagement.writeToFile(words);
        parent.getDictionaryManagement().getMapStringWord().put(target, word);
        parent.getDictionaryManagement().getDictionary().setWordList(words);
        //dictionaryController.setAddWord(true);
        isAdded = true;
    }

    public void setAlertOnWordExist() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText("This word already exists.");
        alert.setContentText("Do you want to edit this word?");

        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes) {
            words.remove(wordExist());
            addWordToFile();
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

    public void setAlertOnSuccessAddWord() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText("Add this word success!");
        alert.showAndWait();
    }

    public void creatAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public void sync(ContainerController parent) {
        this.parent = parent;
    }
}