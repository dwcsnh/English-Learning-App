package com.example.demo.Controller;

import com.example.demo.BasePlus.Word;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.web.WebView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import com.example.demo.BasePlus.GoogleServices;

public class DictionaryController implements Initializable {
    ContainerController parent = new ContainerController();
    ArrayList<Word> word = parent.getDictionaryManagement().getDictionary().getWordList();
    ArrayList<String> listViewWord = new ArrayList<>();
    Map<String, Word> mapStringWord = parent.getDictionaryManagement().getMapStringWord();
    private Word currentWord;

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<String> dictionaryListView;

    @FXML
    private WebView dictionaryWebView;

    @FXML
    private Button speaker;

    @FXML
    private Button favoriteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for(Word x : word) {
            listViewWord.add(x.getSpelling());
        }
//        System.out.println(listViewWord);

        dictionaryListView.getItems().addAll(listViewWord);
        dictionaryListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null){
                        Word selectedWord = mapStringWord.get(newValue.trim());
                        currentWord = selectedWord;
                        setFavoriteButton(currentWord);
                        parent.getHistory().addWordToHistory(selectedWord);
                        String definition = selectedWord.getMeaning();
                        dictionaryWebView.getEngine().loadContent(definition, "text/html");
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
                    Word target = parent.getDictionaryManagement().getDictionary().lookUp(input);
                    if (target != null) {
                        currentWord = target;
                        setFavoriteButton(currentWord);
                        parent.getHistory().addWordToHistory(target);
                        String definition = target.getMeaning();
                        dictionaryWebView.getEngine().loadContent(definition, "text/html");
                    } else {
                        System.out.println("cannot find word");
                    }
                } else {
                    System.out.println("null");
                }
            } else {
                if (!input.isEmpty()) {
                    ArrayList<String> relevantWords = parent.getDictionaryManagement().getSearcher(input);
                    dictionaryListView.getItems().setAll(relevantWords);
                } else {
                    dictionaryListView.getItems().clear();
                    dictionaryListView.getItems().addAll(listViewWord);
                }
            }
        }
    }

    @FXML
    public void updateSearchBar() {
        String spelling = dictionaryListView.getSelectionModel().getSelectedItem();
        if (spelling != null) {
            searchBar.setText(spelling);
        }
    }

    @FXML
    public void readWord(MouseEvent event) {
        if (event.getSource() == speaker) {
            GoogleServices.pronounce(searchBar.getText(), "en");
        }
    }

    @FXML
    public void updateFavoriteList() {
        if (currentWord == null) {
            System.out.println("Current word is null");
        } else {
            if (parent.getFavorite().isExist(currentWord)) {
                System.out.println("This word is already in favorite list");
                parent.getFavorite().removeWordFromFavorite(currentWord);
                favoriteButton.setOpacity(1);
            } else {
                parent.getFavorite().addWordToFavorite(currentWord);
                favoriteButton.setOpacity(0);
            }
            favoriteButton.setMouseTransparent(false);
        }
    }

    public void setFavoriteButton(Word word) {
        if (parent.getFavorite().isExist(word)) {
            favoriteButton.setOpacity(0);
        } else {
            favoriteButton.setOpacity(1);
        }
        favoriteButton.setMouseTransparent(false);
    }

    public void sync(ContainerController parent) {
        if (this.parent == null) {
            System.out.println("null");
        }
        this.parent = parent;
    }
}