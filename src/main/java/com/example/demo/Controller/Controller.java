package com.example.demo.Controller;

import com.example.demo.BasePlus.DictionaryManagement;
import com.example.demo.BasePlus.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {
    DictionaryManagement dictionaryManagement = new DictionaryManagement("data\\E_V.txt");

    ArrayList<Word> word = dictionaryManagement.getDictionary().getWordList();
    ArrayList<String> words= new ArrayList<>();

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<String> listView;

    public Controller() throws IOException {
    }

    @FXML
    void search(ActionEvent event) {
        listView.getItems().clear();
        listView.getItems().addAll(searchList(searchBar.getText(),words));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(Word x : word) {
            words.add(x.getSpelling());
        }
        System.out.println(words);
        listView.getItems().addAll(words);
    }

    private List<String> searchList(String searchWords, List<String> listOfStrings) {

        List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));

        return listOfStrings.stream().filter(input -> {
            return searchWordsArray.stream().allMatch(word ->
                    input.toLowerCase().contains(word.toLowerCase()));
        }).collect(Collectors.toList());
    }
}