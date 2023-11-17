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
    @FXML
    AnchorPane contentPane;
    @FXML
    Button dictionaryButton;
    @FXML
    Button informationButton;
    @FXML
    Button historyButton;
    @FXML
    Button favoriteListButton;
    @FXML
    Button fastEnglishButton;
    @FXML
    Button dictionaryButton21;

    AnchorPane dictionaryPane;
    AnchorPane informationPane;
    AnchorPane historyPane;
    AnchorPane favoritePane;
    AnchorPane fastEnglishPane;
    AnchorPane addWordPane;
    HistoryController historyController;
    DictionaryController dictionaryController;
    FavoriteController favoriteController;
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
        if (dictionaryPane == null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/demo/fxml/dictionary.fxml"));
            dictionaryPane = fxmlLoader.load();
            dictionaryController = fxmlLoader.getController();
        }
        dictionaryController.sync(this);
        contentPane.getChildren().clear();
        contentPane.getChildren().add(dictionaryPane);
    }

    public void showHistoryPane() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/demo/fxml/history.fxml"));
        historyPane = fxmlLoader.load();
        historyController = fxmlLoader.getController();

        historyController.sync(this);
        contentPane.getChildren().clear();
        contentPane.getChildren().add(historyPane);
    }

    public void showFavoritePane() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/demo/fxml/favorite.fxml"));
        favoritePane = fxmlLoader.load();
        favoriteController = fxmlLoader.getController();

        favoriteController.sync(this);
        contentPane.getChildren().clear();
        contentPane.getChildren().add(favoritePane);
    }

    public void showInformationPane() throws IOException {
        if (informationPane == null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/demo/fxml/information.fxml"));
            informationPane = fxmlLoader.load();
        }
        contentPane.getChildren().clear();
        contentPane.getChildren().add(informationPane);
    }

    public void showFastEnglishPane() throws IOException {
        if (fastEnglishPane == null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/demo/fxml/introFastEnglish.fxml"));
            fastEnglishPane = fxmlLoader.load();
            fastEnglishController = fxmlLoader.getController();
        }
        //fastEnglishController.init();
        contentPane.getChildren().clear();
        contentPane.getChildren().add(fastEnglishPane);
    }

    public void showAddWordPane() throws IOException {
        if (addWordPane == null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/demo/fxml/addWord.fxml"));
            addWordPane = fxmlLoader.load();
        }
        contentPane.getChildren().clear();
        contentPane.getChildren().add(addWordPane);
    }
    public void initialize(URL location, ResourceBundle resourceBundle) {
        try {
            showDictionaryPane();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void handleClickComponents(ActionEvent event) throws IOException {
        if (event.getSource() == dictionaryButton) {
            dictionaryManagement.getDictionary().setWordList(new ArrayList<Word>());
            dictionaryManagement.insertFromFile();
            dictionaryController.init();
            showDictionaryPane();
            System.out.println("click dictionary button");
        } else if (event.getSource() == informationButton) {
            showInformationPane();
            System.out.println("click information button");
        } else if (event.getSource() == historyButton) {
            showHistoryPane();
            System.out.println("click history button");
        } else if (event.getSource() == favoriteListButton) {
            showFavoritePane();
            System.out.println("click favorite list button");
        } else if (event.getSource() == fastEnglishButton) {
            showFastEnglishPane();
            System.out.println("click fast list button");
        } else if (event.getSource() == dictionaryButton21) {
            showAddWordPane();
            System.out.println("click addWord button");
        }
    }
}