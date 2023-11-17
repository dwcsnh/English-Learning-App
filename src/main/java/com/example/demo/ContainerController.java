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
    @FXML
    AnchorPane contentPane;

    @FXML
    Button dictionaryButton;
    @FXML
    Button informationButton;
    @FXML
    Button addWordButton;

    AnchorPane dictionaryPane;
    AnchorPane informationPane;
    AnchorPane addWordPane;

    public void showDictionaryPane() throws IOException {
        if (dictionaryPane == null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("dictionary.fxml"));
            dictionaryPane = fxmlLoader.load();
        }
        contentPane.getChildren().clear();
        contentPane.getChildren().add(dictionaryPane);
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

    public void showAddWordPane() throws IOException {
        if (addWordPane == null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("addWord.fxml"));
            addWordPane = fxmlLoader.load();
        }
        contentPane.getChildren().clear();
        contentPane.getChildren().add(addWordPane);
    }


    public void initialize(URL location, ResourceBundle resourceBundle) {
        try {
            showDictionaryPane();
        } catch (IOException e) {
            System.out.println("dictionary to content fault");
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
        } else if (event.getSource() == addWordButton) {
            showAddWordPane();
            System.out.println("click addWord button");
        }
    }
}