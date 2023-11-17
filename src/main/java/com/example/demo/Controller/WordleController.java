package com.example.demo.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.util.Random;
import java.util.regex.Pattern;

public class WordleController {
    @FXML
    private Label label1 = new Label();
    @FXML
    private Label label2 = new Label();
    @FXML
    private Label label3 = new Label();
    @FXML
    private Label label4 = new Label();
    @FXML
    private Label label5 = new Label();
    @FXML
    private Label label6 = new Label();
    @FXML
    private Label label7 = new Label();
    @FXML
    private Label label8 = new Label();
    @FXML
    private Label label9 = new Label();
    @FXML
    private Label label10 = new Label();
    @FXML
    private Label label11 = new Label();
    @FXML
    private Label label12 = new Label();
    @FXML
    private Label label13 = new Label();
    @FXML
    private Label label14 = new Label();
    @FXML
    private Label label15 = new Label();
    @FXML
    private Label label16 = new Label();
    @FXML
    private Label label17 = new Label();
    @FXML
    private Label label18 = new Label();
    @FXML
    private Label label19 = new Label();
    @FXML
    private Label label20 = new Label();
    @FXML
    private Label label21 = new Label();
    @FXML
    private Label label22 = new Label();
    @FXML
    private Label label23 = new Label();
    @FXML
    private Label label24 = new Label();
    @FXML
    private Label label25 = new Label();
    @FXML
    private Label label26 = new Label();
    @FXML
    private Label label27 = new Label();
    @FXML
    private Label label28 = new Label();
    @FXML
    private Label label29 = new Label();
    @FXML
    private Label label30 = new Label();
    @FXML
    private Label youWonLabel = new Label();
    @FXML
    private TextField textField;
    @FXML
    private Button button;
    @FXML
    private Button howToPlay;

    private static final String DATA_WordleWord_PATH = "data\\wordleWord.txt";
    String[] words;

    String ans;
    private int round = 0;
    private boolean win = false;
    private boolean end = false;

    private boolean howToPlayOpen = false;

    public WordleController() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(DATA_WordleWord_PATH), "UTF8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            words = line.split(" ");
        }
        Random random = new Random();
        ans = words[random.nextInt(words.length)].toUpperCase();
        System.out.println(ans);
    }


    @FXML
    public void checkGuess(ActionEvent event) {
        Label[] labels = {
                label1, label2, label3, label4, label5, label6, label7, label8, label9, label10,
                label11, label12, label13, label14, label15, label16, label17, label18, label19, label20,
                label21, label22, label23, label24, label25, label26, label27, label28, label29, label30
        };
        String guess = textField.getText().toUpperCase();
        if (guess.length() != 5 || Pattern.matches(".*[\\W\\d].*", guess)) {
            showInvalidInput();
            textField.clear();
            return;
        }
        for (int i = 0; i < guess.length(); i++) {
            String letter = guess.substring(i, i + 1);
            labels[i + round * 5].setText(letter);
            System.out.println(labels[i + round * 5].getText());
            if (letter.equals(ans.substring(i, i + 1))) {
                labels[i + round * 5].setStyle("-fx-background-radius: 5 5 5 5;" +
                        "-fx-background-color: #538D4E;");
            } else if (ans.contains(letter)) {
                labels[i + round * 5].setStyle("-fx-background-color: #B59F3B;" +
                        "-fx-background-radius: 5 5 5 5");
            } else {
                labels[i + round * 5].setStyle("-fx-background-color: #3A3A3C;" +
                        "-fx-background-radius: 5 5 5 5;");
            }
        }
        if (guess.equals(ans)) {
            win = true;
        }
        System.out.println("end: " + end);
        if (!win) {
            if (!end) {
                nextRound();
            } else {
                System.out.println("You lose!!");
                showLoseTheGame();
                // Do something : show answer, alert, ...
                for (int i = 0; i < 30; i++) {
                    labels[i].setText("");
                    labels[i].setStyle("-fx-background-color: #121213;" +
                            "-fx-border-color: #3A3A3C;" +
                            "-fx-border-width: 5;" +
                            "-fx-border-radius: 5 5 5 5");
                }
                reset();
            }
        } else {
            System.out.println("You win!!");
            // Do something before start a new game
            showWinTheGame();
            //reset
            for (int i = 0; i < 30; i++) {
                labels[i].setText("");
                labels[i].setStyle("-fx-background-color: #121213;" +
                        "-fx-border-color: #3A3A3C;" +
                        "-fx-border-width: 5;" +
                        "-fx-border-radius: 5 5 5 5");
            }
            reset();
            //reset
        }
        textField.clear();
    }

    @FXML
    public void showHowToPlay() {
        if (howToPlayOpen) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/fxml/howToPlay.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("How to Play Wordle");
            stage.setScene(new Scene(root));
            stage.setOnHidden(event -> howToPlayOpen = false);
            howToPlayOpen = true;
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeHowToPlay(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void showWinTheGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/fxml/YouWin.fxml"));
            Parent root = loader.load();
            WordleController wordleController = loader.getController();
            wordleController.youWonLabel.setText("It's: " + ans);

            Stage stage = new Stage();
            stage.setTitle("You Won!!");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeYouWinAndReplay(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void showLoseTheGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/fxml/YouLose.fxml"));
            Parent root = loader.load();
            WordleController wordleController = loader.getController();
            wordleController.youWonLabel.setText("It's: " + ans);

            Stage stage = new Stage();
            stage.setTitle("You Lose!!");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeYouLoseAndReplay(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void showInvalidInput() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/fxml/InvalidInput.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Invalid Input!!");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeInvalidInputAndContinue(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void nextRound() {
        round++;
        if (round > 4) {
            end = true;
        }
    }

    public void reset() {
        round = 0;
        win = false;
        end = false;
        Random random = new Random();
        ans = words[random.nextInt(words.length)].toUpperCase();
        System.out.println(ans);
    }
}
