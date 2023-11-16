package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContainerController implements Initializable {
    private History history = new History("data\\history.txt");
    private DictionaryManagement dictionaryManagement = new DictionaryManagement("data\\E_V.txt");
    @FXML
    AnchorPane contentPane;
    @FXML
    Button dictionaryButton;
    @FXML
    Button informationButton;
    @FXML
    Button historyButton;

    AnchorPane dictionaryPane;
    AnchorPane informationPane;
    AnchorPane historyPane;
    HistoryController historyController;
    DictionaryController dictionaryController;

    public DictionaryManagement getDictionaryManagement() {
        return dictionaryManagement;
    }
    public History getHistory() {
        return history;
    }

    public void showDictionaryPane() throws IOException {
        dictionaryController.sync(this);
        contentPane.getChildren().clear();
        contentPane.getChildren().add(dictionaryPane);
    }

    public void showHistoryPane() throws IOException {
        historyController.sync(this);
        contentPane.getChildren().clear();
        contentPane.getChildren().add(historyPane);
    }

    public void showInformationPane() throws IOException {
        if (informationPane == null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("information.fxml"));
            informationPane = fxmlLoader.load();
        }
        contentPane.getChildren().clear();
        contentPane.getChildren().add(informationPane);
    }

    public void initialize(URL location, ResourceBundle resourceBundle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("dictionary.fxml"));
            dictionaryPane = fxmlLoader.load();
            dictionaryController = fxmlLoader.getController();
            dictionaryController.sync(this);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("history.fxml"));
            historyPane = fxmlLoader.load();
            historyController = fxmlLoader.getController();
            historyController.sync(this);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void handleClickComponents(ActionEvent event) throws IOException {
        if (event.getSource() == dictionaryButton) {
            showDictionaryPane();
            System.out.println("click dictionary button");
        } else if (event.getSource() == informationButton) {
            showInformationPane();
            System.out.println("click information button");
        } else if (event.getSource() == historyButton) {
            showHistoryPane();
            System.out.println("click history button");
        }
    }
}