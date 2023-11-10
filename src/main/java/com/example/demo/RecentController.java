package com.example.demo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class RecentController {
    @FXML
    RecentController controller;
    @FXML
    private ListView<String> listView;
    @FXML
    private WebView definitionView;
    @FXML
    private TextField wordSearch;
    @FXML
    private Button Search;
    @FXML
    private Button recentButton;

    private static final String DATA_FILE_PATH = "data/recent.txt";
    private static final String SPLITTING_CHARACTERS = "<html>";
    private Map<String, Word> data = new HashMap<>();
    private final Recent recent = new Recent();

    public RecentController() {

    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL resourceUrl = getClass().getResource("recent.fxml");
        if (resourceUrl != null) {
            fxmlLoader.setLocation(resourceUrl);
            AnchorPane root = fxmlLoader.load();
            controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            initComponents(scene);
            readData();
            loadWordList();
            Search(scene);
        } else {
            System.err.println("Không tìm thấy file FXML.");
        }

    }

    @FXML
    public TextField getWordSearch() {
        return wordSearch;
    }

    public Button getSearch() {
        return Search;
    }

    public void initComponents(Scene scene) {
        this.definitionView = (WebView) scene.lookup("#definitionView");
        this.listView = (ListView<String>) scene.lookup("#listView");
        RecentController context = this;
        this.listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    //System.out.println(newValue);
                    if (newValue != null) {
                        Word selectedWord = data.get(newValue.trim());
                        String definition = selectedWord.getMeaning();
                        context.definitionView.getEngine().loadContent(definition, "text/html");
                        recentButton = controller.recentButton;
                        recentButton.setOnAction(event -> {
                            try {
                                removeRecentWord();
                                ObservableList<String> originalItems = listView.getItems();
                                originalItems.remove(newValue);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                }
        );
    }

    public void loadWordList() {
        Set<String> List = new HashSet<>();
        Set<String> set = data.keySet();
        for (String key : set ) {
            List.removeIf(e -> e.equals(key));
            List.add(key);
        }
        /*for (String key : List) {
            System.out.println(key);
        }*/
        this.listView.getItems().addAll(List);
    }

    public void loadWordListByWordSearch(String word) {
        int size_word = word.length();
        Set<String> List = new HashSet<>();
        listView.getItems().clear();
        Set<String> set = data.keySet();
        for (String key : set) {
            if (key.length() < size_word) {
                continue;
            }
            String temp = key.substring(0, size_word);
            if (temp.equalsIgnoreCase(word)) {
                List.add(key);
            }
        }
        ArrayList<String> arrayListWord = new ArrayList<>(List);
        Collections.sort(arrayListWord);
        this.listView.getItems().addAll(arrayListWord);
    }

    public void readData() throws IOException {
        FileReader fis = new FileReader(DATA_FILE_PATH);
        BufferedReader br = new BufferedReader(fis);
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(SPLITTING_CHARACTERS);
            String word = parts[0];
            if (word.charAt(0) == '-') {
                word = word.substring(1);
            }
            String definition = SPLITTING_CHARACTERS + parts[1];
            Word wordObj = new Word(word, definition);
            data.put(word, wordObj);
        }
    }

    @FXML
    public void Search(Scene scene) {
        Search = controller.getSearch();
        Search.setOnAction(event -> {
            wordSearch = controller.getWordSearch();
            loadWordListByWordSearch(wordSearch.getText());
        });

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                wordSearch = controller.getWordSearch();
                loadWordListByWordSearch(wordSearch.getText());
            }
        });
    }

    @FXML
    public void removeRecentWord() throws IOException {
        System.out.println("removeFavoriteWord");
        String selectionItem = listView.getSelectionModel().getSelectedItem();
        if (selectionItem != null) {
            /*Set<String> set = data.keySet();
            for (String target : set) {
                System.out.println(target);
            }*/
            //System.out.println(data.get(selectionItem).toString());
            if (data.get(selectionItem) != null) {
                recent.removeWordToRecent(data.get(selectionItem).toString());
                System.out.println(":))");
            }
            data.remove(selectionItem);
        }
    }
}
