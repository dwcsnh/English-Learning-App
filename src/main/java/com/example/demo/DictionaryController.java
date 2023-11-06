package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class DictionaryController implements Initializable {
    DictionaryManagement dictionaryManagement = new DictionaryManagement();

    ArrayList<Word> word = dictionaryManagement.getDictionary();
    ArrayList<String> listViewWord = new ArrayList<>();
    Map<String, Word> mapStringWord = dictionaryManagement.getMapStringWord();

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<String> dictionaryListView;

    @FXML
    private WebView dictionaryWebView;

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
                    Word selectedWord = mapStringWord.get(newValue.trim());
                    String definition = selectedWord.getMeaning();
                    dictionaryWebView.getEngine().loadContent(definition, "text/html");
                }
        );
    }
}
