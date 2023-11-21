package com.example.demo.Controller;

import com.example.demo.BasePlus.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.web.WebView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import com.example.demo.BasePlus.GoogleServices;
import javafx.stage.Stage;

public class DictionaryController extends Controller {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for(Word x : parent.getDictionaryManagement().getDictionary().getWordList()) {
            listViewWord.add(x.getSpelling());
        }

        listView.getItems().addAll(listViewWord);
        listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null){
                        Word selectedWord = parent.getDictionaryManagement().getMapStringWord().get(newValue.trim());
                        currentWord = selectedWord;
                        showWord();
                    }
                }
        );
    }

    @FXML
    public void updateListView(KeyEvent event) {
        if (event.getSource() == searchBar) {
            String input = searchBar.getText();
            if (event.getCode() == KeyCode.ENTER) {
                if (!input.isEmpty()) {
                    Word target = parent.getDictionaryManagement().getMapStringWord().get(input);
                    if (target != null) {
                        currentWord = target;
                        showWord();
                    } else {
                        System.out.println("cannot find word");
                    }
                } else {
                    System.out.println("null");
                }
            } else {
                if (!input.isEmpty()) {
                    ArrayList<String> relevantWords = parent.getDictionaryManagement().getSearcher(input);
                    listView.getItems().setAll(relevantWords);
                    currentWord = null;
                    webView.getEngine().loadContent("");
                } else {
                    listView.getItems().clear();
                    listView.getItems().addAll(listViewWord);
                    webView.getEngine().loadContent("");
                    currentWord = null;
                }
            }
        }
    }

    @FXML
    public void buttonSearch() {
        String input = searchBar.getText();
        if (!input.isEmpty()) {
            Word target = parent.getDictionaryManagement().getMapStringWord().get(input);
            if (target != null) {
                currentWord = target;
                showWord();
            } else {
                System.out.println("cannot find word");
            }
        } else {
            System.out.println("null");
        }
    }

    public void reload() {
        listViewWord.clear();
        for (Word w : parent.getDictionaryManagement().getDictionary().getWordList()) {
            listViewWord.add(w.getSpelling());
        }
        listView.getItems().clear();
        listView.getItems().addAll(listViewWord);
        searchBar.clear();
        webView.getEngine().loadContent("");
    }
}