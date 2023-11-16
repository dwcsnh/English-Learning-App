//package com.example.demo;
//
//
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.web.WebView;
//import org.controlsfx.control.textfield.AutoCompletionBinding;
//import org.controlsfx.control.textfield.TextFields;
//import javafx.scene.control.TextField;
//
//import java.awt.*;
//import java.io.IOException;
//import java.net.URL;
//import java.util.*;
//
//public class SearcherController implements Initializable {
//    DictionaryManagement dictionaryManagement = new DictionaryManagement();
//
//    ArrayList<Word> wordList = dictionaryManagement.getDictionary();
//    Set<String> possibleWordSet = new HashSet<>();
//    Map<String, Word> mapStringWord = dictionaryManagement.getMapStringWord();
//    private AutoCompletionBinding<String> autoCompletionBinding;
//    @FXML
//    protected TextField searchBar;
//
//    @FXML
//    protected WebView resultView;
//
//    public SearcherController() throws IOException {
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        for (Word word : wordList) {
//            possibleWordSet.add(word.getSpelling());
//        }
//        autoCompletionBinding = TextFields.bindAutoCompletion(searchBar, possibleWordSet);
//    }
//}
