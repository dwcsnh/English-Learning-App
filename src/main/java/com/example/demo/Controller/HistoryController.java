package com.example.demo.Controller;

import com.example.demo.BasePlus.GoogleServices;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HistoryController extends Controller {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i = parent.getHistory().getDictionary().getWordList().size() - 1; i >= 0; i--) {
            listViewWord.add(parent.getHistory().getDictionary().getWordList().get(i).getSpelling());
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
            if (event.getCode() == KeyCode.ENTER) {
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
            } else {
                String input = searchBar.getText();
                if (!input.isEmpty()) {
                    ArrayList<String> relevantWords = parent.getHistory().getSearcher(input);
                    listView.getItems().setAll(relevantWords);
                    currentWord = null;
                    webView.getEngine().loadContent("");
                    favoriteButton.setOpacity(1);
                } else {
                    listView.getItems().clear();
                    listView.getItems().addAll(listViewWord);
                    currentWord = null;
                    webView.getEngine().loadContent("");
                    favoriteButton.setOpacity(1);
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

    @Override
    public void showWord() {
        setFavoriteButton(currentWord);
        String definition = currentWord.getMeaning();
        if(listViewWord.contains(currentWord.getSpelling())) {
            webView.getEngine().loadContent(definition, "text/html");
        }
    }

    public void reload() {
        listViewWord.clear();
        for(int i = parent.getHistory().getDictionary().getWordList().size() - 1; i >= 0; i--) {
            listViewWord.add(parent.getHistory().getDictionary().getWordList().get(i).getSpelling());
        }
        listView.getItems().clear();
        listView.getItems().addAll(listViewWord);
        searchBar.clear();
        webView.getEngine().loadContent("");
        favoriteButton.setOpacity(1);
    }
}