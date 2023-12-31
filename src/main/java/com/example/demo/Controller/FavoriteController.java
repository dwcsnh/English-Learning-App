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
import javafx.scene.web.WebView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class FavoriteController extends Controller {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for(Word x : parent.getFavorite().getDictionary().getWordList()) {
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
                    }
                }
            } else {
                if (!input.isEmpty()) {
                    ArrayList<String> relevantWords = parent.getFavorite().getSearcher(input);
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
            }
        }
    }

    public void reload() {
        listViewWord.clear();
        for (Word w : parent.getFavorite().getDictionary().getWordList()) {
            listViewWord.add(w.getSpelling());
        }
        listView.getItems().clear();
        listView.getItems().addAll(listViewWord);
        searchBar.clear();
        webView.getEngine().loadContent("");
        favoriteButton.setOpacity(1);
    }

    @Override
    @FXML
    public void updateFavoriteList() {
        if (currentWord == null) {
            System.out.println("Current word is null");
        } else {
            if (parent.getFavorite().isExist(currentWord)) {
                parent.getFavorite().removeWordFromFavorite(currentWord);
                listViewWord.remove(currentWord.getSpelling());
                listView.getItems().clear();
                listView.getItems().addAll(listViewWord);
                searchBar.clear();
                webView.getEngine().loadContent("");
                favoriteButton.setOpacity(1);
            } else {
                parent.getFavorite().addWordToFavorite(currentWord);
                favoriteButton.setOpacity(0);
            }
            favoriteButton.setMouseTransparent(false);
        }
    }
}