package com.example.demo.Controller;

import com.example.demo.BasePlus.GoogleServices;
import com.example.demo.BasePlus.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.web.WebView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class FavoriteController implements Initializable {
    ContainerController parent = new ContainerController();
    ArrayList<Word> word = parent.getFavorite().getDictionary().getWordList();
    ArrayList<String> listViewWord = new ArrayList<>();
    Map<String, Word> mapStringWord = parent.getFavorite().getMapStringWord();
    private Word currentWord;

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<String> favoriteListView;

    @FXML
    private WebView favoriteWebView;

    @FXML
    private Button speaker;

    @FXML
    private Button favoriteButton;

    private EditWordController editWordController = new EditWordController();

    private boolean editWordOpen = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for(Word x : word) {
            listViewWord.add(x.getSpelling());
        }
//        System.out.println(listViewWord);

        favoriteListView.getItems().addAll(listViewWord);
        favoriteListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null){
                        Word selectedWord = mapStringWord.get(newValue.trim());
                        currentWord = selectedWord;
                        setFavoriteButton(currentWord);
                        parent.getHistory().addWordToHistory(selectedWord);
                        String definition = selectedWord.getMeaning();
                        favoriteWebView.getEngine().loadContent(definition, "text/html");
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
                    Word target = parent.getFavorite().getDictionary().lookUp(input);
                    if (target != null) {
                        currentWord = target;
                        setFavoriteButton(currentWord);
                        parent.getHistory().addWordToHistory(target);
                        String definition = target.getMeaning();
                        favoriteWebView.getEngine().loadContent(definition, "text/html");
                    } else {
                        System.out.println("cannot find word");
                    }
                } else {
                    System.out.println("null");
                }
            } else {
                if (!input.isEmpty()) {
                    ArrayList<String> relevantWords = parent.getFavorite().getSearcher(input);
                    favoriteListView.getItems().setAll(relevantWords);
                } else {
                    favoriteListView.getItems().clear();
                    favoriteListView.getItems().addAll(listViewWord);
                }
            }
        }
    }

    @FXML
    private void showEditWordPane(ActionEvent event) {
        if (editWordOpen) {
            return;
        }
        try {
            String selectedWord = favoriteListView.getSelectionModel().getSelectedItem();
            if (selectedWord != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/fxml/editWord.fxml"));
                Parent root = loader.load();

                editWordController = loader.getController();

                editWordController.setCurrentWordLabel(selectedWord);

                editWordController.setWebView(mapStringWord.get(selectedWord).getMeaning());

                editWordController.setMapStringWord(mapStringWord);

                Stage stage = new Stage();
                stage.setTitle("Edit Word");
                stage.setScene(new Scene(root));
                Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/demo/logo/B.png")));
                stage.getIcons().add(icon);
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
        String newdefinition = mapStringWord.get(favoriteListView.getSelectionModel().getSelectedItem()).getMeaning();
        favoriteWebView.getEngine().loadContent(newdefinition, "text/html");
        editWordOpen = false;
        parent.getDictionaryManagement().writeToFile(word);
    }

    public void removeWord() {
        if (currentWord != null) {
            parent.getDictionaryManagement().getMapStringWord().remove(currentWord.getSpelling());
            parent.getDictionaryManagement().removeWordFromFile(currentWord);
            parent.getHistory().removeWordFromFile(currentWord);
            parent.getFavorite().removeWordFromFile(currentWord);
            word = parent.getFavorite().getDictionary().getWordList();
            listViewWord.clear();
            for (Word w : word) {
                listViewWord.add(w.getSpelling());
            }
            favoriteListView.getItems().clear();
            favoriteListView.getItems().addAll(listViewWord);
            favoriteWebView.getEngine().loadContent("");
            searchBar.clear();
        }
    }

    @FXML
    public void updateSearchBar() {
        String spelling = favoriteListView.getSelectionModel().getSelectedItem();
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