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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

abstract class Controller implements Initializable {
    protected Word currentWord;
    protected EditWordController editWordController = new EditWordController();
    protected boolean editWordOpen = false;
    protected ContainerController parent = new ContainerController();
    protected ArrayList<String> listViewWord = new ArrayList<>();

    @FXML
    protected TextField searchBar;

    @FXML
    protected ListView<String> listView;

    @FXML
    protected WebView webView;

    @FXML
    protected Button speaker;

    @FXML
    protected Button favoriteButton;

    @FXML
    abstract void updateListView(KeyEvent event);

    @FXML
    abstract void buttonSearch();

    public void showWord() {
        setFavoriteButton(currentWord);
        parent.getHistory().addWordToHistory(currentWord);
        String definition = currentWord.getMeaning();
        if(listViewWord.contains(searchBar.getText())) {
            webView.getEngine().loadContent(definition, "text/html");
        }
    }

    @FXML
    public void showEditWordPane(ActionEvent event) {
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
                Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/demo/icon/B.png")));
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

    abstract void reload();

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
                currentWord = null;
                reload();
            }
        } else {
            showAlert("Empty word!", "Please choose a word!");
        }
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void updateSearchBar() {
        String spelling = listView.getSelectionModel().getSelectedItem();
        if (spelling != null) {
            searchBar.setText(spelling);
        }
    }

    @FXML
    public void readWord(MouseEvent event) {
        if (event.getSource() == speaker) {
            if (!searchBar.getText().isEmpty()) {
                GoogleServices.pronounce(searchBar.getText(), "en");
            } else {
                showAlert("Empty word!", "Please choose a word.");
            }
        }
    }

    @FXML
    public void updateFavoriteList() {
        if (currentWord == null) {
            showAlert("Empty word!", "Please choose a word.");
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