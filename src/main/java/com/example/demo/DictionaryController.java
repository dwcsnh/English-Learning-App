package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.web.WebView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class DictionaryController implements Initializable {
    DictionaryManagement dictionaryManagement = new DictionaryManagement();
    GoogleServices googleServices = new GoogleServices();
    ArrayList<Word> word = dictionaryManagement.getDictionary().getWordList();
    ArrayList<String> listViewWord = new ArrayList<>();
    Map<String, Word> mapStringWord = dictionaryManagement.getMapStringWord();

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<String> dictionaryListView;

    @FXML
    private WebView dictionaryWebView;

    @FXML
    private Button speaker;

    public DictionaryController() throws IOException {
    }

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
                        String definition = selectedWord.getMeaning();
                        dictionaryWebView.getEngine().loadContent(definition, "text/html");
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
                    Word target = dictionaryManagement.getDictionary().lookUp(input);
                    if (target != null) {
                        String definition = target.getMeaning();
                        dictionaryWebView.getEngine().loadContent(definition, "text/html");
                    } else {
                        System.out.println("cannot find word");
                    }
                } else {
                    System.out.println("null");
                }
            } else {
                String input = searchBar.getText();
                if (!input.isEmpty()) {
                    ArrayList<String> relevantWords = dictionaryManagement.getSearcher(input);
                    dictionaryListView.getItems().setAll(relevantWords);
                } else {
                    dictionaryListView.getItems().clear();
                }
            }
        }
    }

    @FXML
    public void updateSearchBar(MouseEvent event) {
        String spelling = dictionaryListView.getSelectionModel().getSelectedItem();
        if (spelling != null) {
            searchBar.setText(spelling);
        }
    }

    @FXML
    public void handleSearchBar(KeyEvent event) {
        if (event.getSource() == searchBar) {
            System.out.println("typing");
        }
    }

    @FXML
    public void readWord(MouseEvent event) {
        if (event.getSource() == speaker) {
            googleServices.pronounce(searchBar.getText());
        }
    }
}