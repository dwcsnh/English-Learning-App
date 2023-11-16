package com.example.demo;

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

public class FavoriteController implements Initializable {
    ContainerController parent = new ContainerController();
    ArrayList<Word> favoriteList = parent.getFavorite().getDictionary().getWordList();
    ArrayList<String> listViewWord = new ArrayList<>();
    Map<String, Word> mapStringWord = parent.getFavorite().getMapStringWord();

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<String> favoriteListView;

    @FXML
    private WebView favoriteWebView;

    @FXML
    private Button speaker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i = favoriteList.size() - 1; i >= 0; i--) {
            listViewWord.add(favoriteList.get(i).getSpelling());
        }

        favoriteListView.getItems().addAll(listViewWord);
        favoriteListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null){
                        Word selectedWord = mapStringWord.get(newValue.trim());
                        String definition = selectedWord.getMeaning();
                        favoriteWebView.getEngine().loadContent(definition, "text/html");
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
                        String definition = target.getMeaning();
                        favoriteWebView.getEngine().loadContent(definition, "text/html");
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
                    favoriteListView.getItems().setAll(relevantWords);
                } else {
                    favoriteListView.getItems().clear();
                }
            }
            parent.getHistory().print();
        }
    }

    @FXML
    public void updateSearchBar(MouseEvent event) {
        String spelling = favoriteListView.getSelectionModel().getSelectedItem();
        if (spelling != null) {
            searchBar.setText(spelling);
        }
    }

    @FXML
    public void readWord(MouseEvent event) {
        if (event.getSource() == speaker) {
            GoogleServices.pronounce(searchBar.getText());
        }
    }

    public void sync(ContainerController parent) {
        this.parent = parent;
    }
}