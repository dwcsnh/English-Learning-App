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
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ContainerController implements Initializable {
    private History history = new History("data\\history.txt");
    private Favorite favorite = new Favorite("data\\favorite.txt");
    private DictionaryManagement dictionaryManagement = new DictionaryManagement("data\\E_V.txt");
    private Button lastClickedButton;
    private boolean fastEng = false;
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
    @FXML
    AnchorPane sidebar;

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

    public boolean isFastEng() {
        return fastEng;
    }

    public void setFastEng(boolean fastEng) {
        this.fastEng = fastEng;
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
        fastEnglishController.setContainerController(this);
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
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        dictionaryButton.setTooltip(new Tooltip("Dictionary"));
        dictionaryButton.getTooltip().getStyleClass().add("tooltip");
        googleTranslateButton.setTooltip(new Tooltip("Google Translate"));
        googleTranslateButton.getTooltip().getStyleClass().add("tooltip");
        historyButton.setTooltip(new Tooltip("History"));
        historyButton.getTooltip().getStyleClass().add("tooltip");
        favoriteListButton.setTooltip(new Tooltip("Favorite"));
        favoriteListButton.getTooltip().getStyleClass().add("tooltip");
        addWordButton.setTooltip(new Tooltip("Add Word"));
        addWordButton.getTooltip().getStyleClass().add("tooltip");
        wordleButton.setTooltip(new Tooltip("Wordle"));
        wordleButton.getTooltip().getStyleClass().add("tooltip");
        fastEnglishButton.setTooltip(new Tooltip("Fast English"));
        fastEnglishButton.getTooltip().getStyleClass().add("tooltip");
    }

    @FXML
    public void handleClickComponents(ActionEvent event) throws IOException {
        if (lastClickedButton != null) {
            lastClickedButton.getStyleClass().remove("clicked");
            if (lastClickedButton == wordleButton) {
                sidebar.getStyleClass().remove("wordle");
                wordleButton.getStyleClass().remove("wordleClicked");
                for (Button btn : Arrays.asList(dictionaryButton, historyButton, favoriteListButton,
                        googleTranslateButton, addWordButton, fastEnglishButton)) {
                    btn.getStyleClass().remove("wordle");
                }
            } else if (lastClickedButton == fastEnglishButton) {
                sidebar.getStyleClass().remove("fastEnglish");
                fastEnglishButton.getStyleClass().remove("fastEnglishClicked");
                for (Button btn : Arrays.asList(dictionaryButton, historyButton, favoriteListButton,
                        googleTranslateButton, addWordButton, wordleButton)) {
                    btn.getStyleClass().remove("fastEnglish");
                }
            }
        }
        if (fastEng && event.getSource() != fastEnglishController) {
            fastEng = false;
            //System.out.println(fastEng);
        }
        if (event.getSource() == dictionaryButton) {
            showDictionaryPane();
            dictionaryButton.getStyleClass().add("clicked");
            lastClickedButton = dictionaryButton;
            System.out.println("click dictionary button");
        } else if (event.getSource() == historyButton) {
            showHistoryPane();
            historyButton.getStyleClass().add("clicked");
            lastClickedButton = historyButton;
            System.out.println("click history button");
        } else if (event.getSource() == favoriteListButton) {
            showFavoritePane();
            favoriteListButton.getStyleClass().add("clicked");
            lastClickedButton = favoriteListButton;
            System.out.println("click favorite list button");
        } else if (event.getSource() == wordleButton) {
            showWordlePane();
            wordleButton.getStyleClass().add("wordleClicked");
            sidebar.getStyleClass().add("wordle");
            for (Button btn : Arrays.asList(dictionaryButton, historyButton, favoriteListButton,
                    googleTranslateButton, addWordButton, fastEnglishButton)) {
                btn.getStyleClass().add("wordle");
            }
            lastClickedButton = wordleButton;
            System.out.println("click wordle button");
        } else if (event.getSource() == googleTranslateButton) {
            showGoogleTranslatePane();
            googleTranslateButton.getStyleClass().add("clicked");
            lastClickedButton = googleTranslateButton;
            System.out.println("click google translate button");
        } else if (event.getSource() == addWordButton) {
            showAddWordPane();
            addWordButton.getStyleClass().add("clicked");
            lastClickedButton = addWordButton;
            System.out.println("click addWord button");
        } else if (event.getSource() == fastEnglishButton) {
            fastEng = true;
            showFastEnglishPane();
            fastEnglishButton.getStyleClass().add("fastEnglishClicked");
            sidebar.getStyleClass().add("fastEnglish");
            for (Button btn : Arrays.asList(dictionaryButton, historyButton, favoriteListButton,
                    googleTranslateButton, addWordButton, wordleButton)) {
                btn.getStyleClass().add("fastEnglish");
            }
            lastClickedButton = fastEnglishButton;
            System.out.println("click fastEnglish button");
        }
    }
}