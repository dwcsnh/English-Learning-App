package com.example.demo.Controller;

import com.example.demo.BasePlus.DictionaryManagement;
import com.example.demo.BasePlus.Favorite;
import com.example.demo.BasePlus.History;
import com.example.demo.BasePlus.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ContainerController implements Initializable {
    private History history = new History("data\\history.txt");
    private Favorite favorite = new Favorite("data\\favorite.txt");
    private DictionaryManagement dictionaryManagement = new DictionaryManagement("data\\E_V.txt");
    private boolean pickAddWord = false;
    @FXML
    AnchorPane contentPane;
    @FXML
    Button dictionaryButton;
    @FXML
    Button historyButton;
    @FXML
    Button favoriteListButton;
    @FXML
    Button wordleButton;
    @FXML
    Button googleTranslateButton;
    @FXML
    Button addWordButton;
    @FXML
    Button fastEnglishButton;

    AnchorPane dictionaryPane;
    AnchorPane informationPane;
    AnchorPane historyPane;
    AnchorPane favoritePane;
    AnchorPane wordlePane;
    AnchorPane addWordPane;
    AnchorPane googleTranslatePane;
    AnchorPane fastEnglishPane;
    HistoryController historyController;
    DictionaryController dictionaryController;
    FavoriteController favoriteController;
    WordleController wordleController;
    GoogleTranslateController googleTranslateController;
    AddWordController addWordController;
    FastEnglishController fastEnglishController;

    public DictionaryManagement getDictionaryManagement() {
        return dictionaryManagement;
    }

    public History getHistory() {
        return history;
    }

    public Favorite getFavorite() {
        return favorite;
    }

    public void showDictionaryPane() throws IOException {
        if(dictionaryManagement.wordListChanged()) {
            dictionaryController.reload();
        }
        dictionaryController.sync(this);
        contentPane.getChildren().clear();
        contentPane.getChildren().add(dictionaryPane);
    }

    public void showHistoryPane() throws IOException {
        if(history.wordListChanged()) {
            historyController.reload();
        }
        historyController.sync(this);
        contentPane.getChildren().clear();
        contentPane.getChildren().add(historyPane);
    }

    public void showWordlePane() {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(wordlePane);
    }

    public void showFavoritePane() throws IOException {
        if (favorite.wordListChanged()) {
            favoriteController.reload();
        }
        favoriteController.sync(this);
        contentPane.getChildren().clear();
        contentPane.getChildren().add(favoritePane);
    }

    public void showGoogleTranslatePane() {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(googleTranslatePane);
    }

    public void showAddWordPane() {
        addWordController.sync(this);
        contentPane.getChildren().clear();
        contentPane.getChildren().add(addWordPane);
    }

    public void showFastEnglishPane() {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(fastEnglishPane);
    }

    public void initialize(URL location, ResourceBundle resourceBundle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/demo/fxml/dictionary.fxml"));
            dictionaryPane = fxmlLoader.load();
            dictionaryController = fxmlLoader.getController();
            dictionaryController.sync(this);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/demo/fxml/history.fxml"));
            historyPane = fxmlLoader.load();
            historyController = fxmlLoader.getController();
            historyController.sync(this);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/demo/fxml/favorite.fxml"));
            favoritePane = fxmlLoader.load();
            favoriteController = fxmlLoader.getController();
            favoriteController.sync(this);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/demo/fxml/NewWordle.fxml"));
            wordlePane = fxmlLoader.load();
            wordleController = fxmlLoader.getController();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/demo/fxml/google translate.fxml"));
            googleTranslatePane = fxmlLoader.load();
            googleTranslateController = fxmlLoader.getController();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/demo/fxml/addWord.fxml"));
            addWordPane = fxmlLoader.load();
            addWordController = fxmlLoader.getController();
            addWordController.sync(this);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/demo/fxml/introFastEnglish.fxml"));
            fastEnglishPane = fxmlLoader.load();
            fastEnglishController = fxmlLoader.getController();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } try {
            showDictionaryPane();
            dictionaryButton.setStyle("-fx-background-color: #EBEBEB;");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void resetButtonStyle() {
        dictionaryButton.setStyle("-fx-background-color: transparent;");
        historyButton.setStyle("-fx-background-color: transparent;");
        favoriteListButton.setStyle("-fx-background-color: transparent;");
        wordleButton.setStyle("-fx-background-color: transparent;");
        googleTranslateButton.setStyle("-fx-background-color: transparent;");
        addWordButton.setStyle("-fx-background-color: transparent;");
        fastEnglishButton.setStyle("-fx-background-color: transparent;");
    }

    @FXML
    public void handleClickComponents(ActionEvent event) throws IOException {
        if (event.getSource() == dictionaryButton) {
            resetButtonStyle();
            dictionaryButton.setStyle("-fx-background-color: #EBEBEB;");
            showDictionaryPane();
            System.out.println("click dictionary button");
        } else if (event.getSource() == historyButton) {
            resetButtonStyle();
            historyButton.setStyle("-fx-background-color: #EBEBEB;");
            showHistoryPane();
            System.out.println("click history button");
        } else if (event.getSource() == favoriteListButton) {
            resetButtonStyle();
            favoriteListButton.setStyle("-fx-background-color: #EBEBEB;");
            showFavoritePane();
            System.out.println("click favorite list button");
        } else if (event.getSource() == wordleButton) {
            resetButtonStyle();
            wordleButton.setStyle("-fx-background-color: #EBEBEB;");
            showWordlePane();
            System.out.println("click wordle button");
        } else if (event.getSource() == googleTranslateButton) {
            resetButtonStyle();
            googleTranslateButton.setStyle("-fx-background-color: #EBEBEB;");
            showGoogleTranslatePane();
            System.out.println("click google translate button");
        } else if (event.getSource() == addWordButton) {
            resetButtonStyle();
            addWordButton.setStyle("-fx-background-color: #EBEBEB;");
            showAddWordPane();
            System.out.println("click addWord button");
        } else if (event.getSource() == fastEnglishButton) {
            resetButtonStyle();
            fastEnglishButton.setStyle("-fx-background-color: #EBEBEB;");
            showFastEnglishPane();
            System.out.println("click fastEnglish button");
        }
    }
}