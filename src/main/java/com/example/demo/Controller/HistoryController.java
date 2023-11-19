package com.example.demo.Controller;

import com.example.demo.BasePlus.GoogleServices;
import com.example.demo.BasePlus.Word;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {
    ContainerController parent = new ContainerController();
    ArrayList<Word> historyList = parent.getHistory().getDictionary().getWordList();
    ArrayList<String> listViewWord = new ArrayList<>();
    Map<String, Word> mapStringWord = parent.getHistory().getMapStringWord();
    private Word currentWord;

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<String> historyListView;

    @FXML
    private WebView historyWebView;

    @FXML
    private Button speaker;

    @FXML
    private Button favoriteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i = historyList.size() - 1; i >= 0; i--) {
            listViewWord.add(historyList.get(i).getSpelling());
        }

        historyListView.getItems().addAll(listViewWord);
        historyListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null){
                        Word selectedWord = mapStringWord.get(newValue.trim());
                        currentWord = selectedWord;
                        setFavoriteButton(currentWord);
                        String definition = selectedWord.getMeaning();
                        historyWebView.getEngine().loadContent(definition, "text/html");
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
                    Word target = parent.getHistory().getDictionary().lookUp(input);
                    if (target != null) {
                        currentWord = target;
                        setFavoriteButton(currentWord);
                        String definition = target.getMeaning();
                        historyWebView.getEngine().loadContent(definition, "text/html");
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
                    historyListView.getItems().setAll(relevantWords);
                } else {
                    historyListView.getItems().clear();
                }
            }
            parent.getHistory().print();
        }
    }

    @FXML
    public void updateSearchBar(MouseEvent event) {
        String spelling = historyListView.getSelectionModel().getSelectedItem();
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
        this.parent = parent;
    }
}
