package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.web.WebView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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

    @FXML
    private Label currentWordLabel;

    @FXML
    private WebView editWebView;

    @FXML
    private TextArea editTextArea;

    private EditWordController editWordController = new EditWordController();

    private boolean editWordOpen = false;


    public DictionaryController() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for (Word x : word) {
            listViewWord.add(x.getSpelling());
        }
//        System.out.println(listViewWord);

        dictionaryListView.getItems().addAll(listViewWord);


        dictionaryListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
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

                        dictionaryListView.getSelectionModel().select(target.getSpelling());
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
                    dictionaryListView.getItems().addAll(listViewWord);
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

    @FXML
    private void showEditWordPane(ActionEvent event) {
        if (editWordOpen) {
            return;
        }
        try {
            String selectedWord = dictionaryListView.getSelectionModel().getSelectedItem();
            if (selectedWord != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("editWord.fxml"));
                Parent root = loader.load();

                editWordController = loader.getController();

                editWordController.setCurrentWordLabel(selectedWord);

                editWordController.setWebView(mapStringWord.get(selectedWord).getMeaning());

                editWordController.setMapStringWord(mapStringWord);

                Stage stage = new Stage();
                stage.setTitle("Edit Word");
                stage.setScene(new Scene(root));
                stage.setOnHidden(e -> resetMapAndWebView());
                editWordOpen = true;
                stage.show();
            } else {
                editWordController.showUnselectedWord();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetMapAndWebView() {
        String newdefinition = mapStringWord.get(dictionaryListView.getSelectionModel().getSelectedItem()).getMeaning();
        dictionaryWebView.getEngine().loadContent(newdefinition, "text/html");
        editWordOpen = false;
        dictionaryManagement.writeToFile(word);
    }
}