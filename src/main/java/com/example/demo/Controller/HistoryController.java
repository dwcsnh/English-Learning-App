package com.example.demo.Controller;

import com.example.demo.BasePlus.GoogleServices;
import com.example.demo.BasePlus.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HistoryController implements Initializable {
    ContainerController parent = new ContainerController();
    ArrayList<String> listViewWord = new ArrayList<>();
    private Word currentWord;

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<String> listView;

    @FXML
    private WebView webView;

    @FXML
    private Button speaker;

    @FXML
    private Button favoriteButton;

    private EditWordController editWordController = new EditWordController();

    private boolean editWordOpen = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i = parent.getHistory().getDictionary().getWordList().size() - 1; i >= 0; i--) {
            listViewWord.add(parent.getHistory().getDictionary().getWordList().get(i).getSpelling());
        }

        listView.getItems().addAll(listViewWord);
        listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null){
                        Word selectedWord = parent.getDictionaryManagement().getMapStringWord().get(newValue.trim());
                        currentWord = selectedWord;
                        setFavoriteButton(currentWord);
                        String definition = selectedWord.getMeaning();
                        webView.getEngine().loadContent(definition, "text/html");
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
                    Word target = parent.getDictionaryManagement().getMapStringWord().get(input);
                    if (target != null) {
                        currentWord = target;
                        setFavoriteButton(currentWord);
                        String definition = target.getMeaning();
                        webView.getEngine().loadContent(definition, "text/html");
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
                    listView.getItems().setAll(relevantWords);
                    currentWord = null;
                    webView.getEngine().loadContent("");
                } else {
                    listView.getItems().clear();
                    listView.getItems().addAll(listViewWord);
                    currentWord = null;
                    webView.getEngine().loadContent("");
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
            String selectedWord = listView.getSelectionModel().getSelectedItem();
            if (selectedWord != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/fxml/editWord.fxml"));
                Parent root = loader.load();

                editWordController = loader.getController();
                editWordController.sync(this.parent);
                editWordController.setCurrentWordLabel(selectedWord);
                editWordController.setWebView(parent.getDictionaryManagement().getMapStringWord().get(selectedWord).getMeaning());

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
        String newdefinition = parent.getDictionaryManagement().getMapStringWord().get(listView.getSelectionModel().getSelectedItem()).getMeaning();
        webView.getEngine().loadContent(newdefinition, "text/html");
        editWordOpen = false;
    }

    public void reload() {
        listViewWord.clear();
        for(int i = parent.getHistory().getDictionary().getWordList().size() - 1; i >= 0; i--) {
            listViewWord.add(parent.getHistory().getDictionary().getWordList().get(i).getSpelling());
        }
        listView.getItems().clear();
        listView.getItems().addAll(listViewWord);
        searchBar.clear();
        webView.getEngine().loadContent("");
    }

    public void removeWord() {
        if (currentWord != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning!");
            alert.setHeaderText(currentWord.getSpelling() + " will be removed!");
            alert.setContentText("Do you want to remove?");

            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeYes) {
                parent.getDictionaryManagement().getMapStringWord().remove(currentWord.getSpelling());
                parent.getDictionaryManagement().removeWordFromFile(currentWord);
                parent.getHistory().removeWordFromFile(currentWord);
                parent.getFavorite().removeWordFromFile(currentWord);
                reload();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning!");
            alert.setHeaderText(null);
            alert.setContentText("No word chosen!");
            alert.showAndWait();
        }
    }

    @FXML
    public void updateSearchBar(MouseEvent event) {
        String spelling = listView.getSelectionModel().getSelectedItem();
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
