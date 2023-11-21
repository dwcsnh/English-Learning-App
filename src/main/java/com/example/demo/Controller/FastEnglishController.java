package com.example.demo.Controller;

import com.example.demo.BasePlus.GoogleServices;
import com.example.demo.BasePlus.HelloApplication;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.Media;
import javafx.stage.Stage;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FastEnglishController {
    private final static String DATA_FILE_PATH = "data\\wordsFastEng.txt";
    private final static int TIME_OF_GAME = 1000;
    private ArrayList<String> wordsList = new ArrayList<>();

    private ArrayList<Integer> arrNumbers = new ArrayList<>();

    private GoogleServices googleServices = new GoogleServices();

    private String word;
    private int result;
    private int yourAnswer;
    private int turn;
    private doWord task;
    private boolean checkYourChoose;
    private boolean lostGame;
    private boolean winGame;
    private boolean restartGame;
    private int curScoreNumber;
    private int highScoreNumber;
    private boolean preview = false;

    @FXML
    AnchorPane contentPane;
    @FXML
    private Label wordLabel;
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView4;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label highScoreLabel;
    @FXML
    private Label curScoreLabel;
    @FXML
    private CheckBox readCheckBox;
    @FXML
    private CheckBox labelWordCheckBox;
    @FXML
    private MediaView wrongAnswerMedia;



    public FastEnglishController() {

        curScoreNumber = -100;
        winGame = false;
        restartGame = false;
        turn = -1;
        yourAnswer = 0;
        result = 0;
        checkYourChoose = false;
        lostGame = false;
    }

    @FXML
    private Button playButton;
    @FXML
    public void PlayGame() throws IOException {
        System.out.println("Play");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/fxml/FastEnglish.fxml"));
        //Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) playButton.getScene().getWindow();
        //stage.setScene(scene);*/
        contentPane.getChildren().clear();
        contentPane.getChildren().add(fxmlLoader.load());
        FastEnglishController fastEnglishController = fxmlLoader.getController();
        stage.setOnCloseRequest(event -> {
            fastEnglishController.shutdown();
        });
        fastEnglishController.init();
    }

    @FXML
    public void Preview() {
        if (preview) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/example/demo/fxml/previewFastEnglish.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Preview");
            stage.setScene(scene);
            stage.setOnHidden(event -> preview = false);
            preview = true;
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        //task = new doWord();
        readData();
        //inGame();

        /*wordLabel.setText(word);
        wordLabel.setAlignment(Pos.CENTER);*/
        loadLabel(wordLabel, word);
        //readCheckBox.setSelected(true);
        labelWordCheckBox.setSelected(true);
        loadHighScore();

        task = new doWord();
        progressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
        inGame();
        System.out.println(curScoreNumber);
    }

    public void readData() {
        File file = new File(DATA_FILE_PATH);
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    System.out.println("Couldn't create file!");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Couldn't create file!");
            }
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader
                (new FileInputStream(DATA_FILE_PATH), "UTF-8"))) {
            wordsList.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                wordsList.add(line);
            }
            int l = wordsList.size();
            for (int i = 1; i <= l; i++) {
                arrNumbers.add(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't create file!");
        }
    }

    public void loadImage(ImageView imageView, String word) {
        String fileName = "/com/example/demo/image/" + word + ".png";
        Image image = new Image(getClass().getResourceAsStream(fileName));
        imageView.setImage(image);
    }

    public void loadImageToGame(String[] arrWords) {
        Random random = new Random();
        result = 0;
        for (int i = 0; i < 4; i++) {
            int ran = i + random.nextInt(4 - i);
            if (arrWords[i].equals(word)) {
                result = ran + 1;
            }
            swap(arrWords, i, ran);
        }
        for (int i = 0; i < 4; i++) {
            if (arrWords[i].equals(word)) {
                result = i + 1;
                break;
            }
        }
        System.out.println("r = " + result);
        loadImage(imageView1, arrWords[0]);
        loadImage(imageView2, arrWords[1]);
        loadImage(imageView3, arrWords[2]);
        loadImage(imageView4, arrWords[3]);
    }

    public void play() {
        Random random = new Random();
        String arr[] = new String[4];
        ArrayList<String> tmp = new ArrayList<>();
        for (int i = 0; i < wordsList.size(); i++) {
            tmp.add(wordsList.get(i));
        }
        if (turn < wordsList.size()) {
            int ran = turn + random.nextInt(wordsList.size() - turn);
            swap(wordsList, turn, ran);
            arr[0] = wordsList.get(turn);
            System.out.print(wordsList.get(turn) + " ");
            word = wordsList.get(turn);
            if (labelWordCheckBox.isSelected()) {
                loadLabel(wordLabel, word);
            } else {
                loadLabel(wordLabel, "");
            }
            for (int j = 0; j < 3; j++) {
                int randomIndex;
                do {
                    randomIndex = j + random.nextInt(tmp.size() - j - 1) + 1;
                } while (tmp.get(randomIndex).equals(wordsList.get(turn)));
                swap(tmp, j, randomIndex);
                arr[j + 1] = tmp.get(j);
            }
            System.out.println();
            loadImageToGame(arr);
        } else {
            winGame();
        }
    }


    public void inGame() {
        double progressVal = progressBar.getProgress();
        if (progressVal != 0.0 && !winGame) {
            if (yourAnswer == result) {
                //System.out.println("correct");
                playSoundOnCorrectAnswer();
                curScoreNumber += 100;
                loadLabel(curScoreLabel,"SCORE: " + curScoreNumber);
                checkYourChoose = true;
                turn++;
                play();
                if (readCheckBox.isSelected()) {
                    readWord();
                }
                yourAnswer = -1;
            } else {
                playSoundOnWrongAnswer();
                curScoreNumber -= 30;
                if (curScoreNumber < 0) {
                    curScoreNumber = 0;
                }
                loadLabel(curScoreLabel, "SCORE: " + curScoreNumber);
                System.out.println("playSound");
                System.out.println(false);
            }
        }
    }

    public void restartGame() {
        turn = -1;
        curScoreNumber = -100;
        winGame = false;
        yourAnswer = 0;
        result = 0;
        checkYourChoose = false;
        lostGame = false;
        //task = new doWord();
        readData();
        loadLabel(wordLabel, word);
        //task = new doWord();
        System.out.println(lostGame);
        task.cancel();
        progressBar.progressProperty().unbind();
        task =new doWord();
        progressBar.setProgress(1);
        progressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
        inGame();
    }

    public void lostGame() {
        Image image = new Image(getClass().getResourceAsStream("/com/example/demo/image/lostGame.png"));
        imageView1.setImage(image);
        imageView2.setImage(image);
        imageView3.setImage(image);
        imageView4.setImage(image);
        Platform.runLater(() -> {
            wordLabel.setText("Game Over!!!");
        });
        playSoundOnLostGame();
        exportHighScoreToFile();
    }

    public void winGame() {
        playSoundOnWinGame();
        Image image = new Image(getClass().getResourceAsStream("/com/example/demo/image/winGame.png"));
        imageView1.setImage(image);
        imageView2.setImage(image);
        imageView3.setImage(image);
        imageView4.setImage(image);
        Platform.runLater(() -> {
            wordLabel.setText("You winn!!!");
        });
        winGame = true;
        exportHighScoreToFile();
    }

    public void loadHighScore() {
        readHighScore();
        loadLabel(highScoreLabel, "HIGHSCORE: " + highScoreNumber);
    }

    public void readHighScore() {
        File file = new File("data\\highScore.txt");
        try {
            Scanner scanner = new Scanner(file);
            highScoreNumber = scanner.nextInt();
            System.out.println(highScoreNumber);
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found!");
        }
    }

    public void exportHighScoreToFile() {
        File file = new File("data\\highScore.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            if (curScoreNumber > highScoreNumber) {
                highScoreNumber = curScoreNumber;
            }
            writer.write(highScoreNumber + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing to the file!");
        }
    }

    public void loadLabel(Label label, String s) {
        label.setText(s);
        label.setAlignment(Pos.CENTER);
    }

    public void readWord() {
        googleServices.pronounce(word, "en");
    }

    public void playSoundOnLostGame() {
        playSound("/com/example/demo/audio/lostGame.mp3");
    }

    public void playSoundOnWrongAnswer() {
        playSound("/com/example/demo/audio/wrongAnswer.mp3");
    }

    public void playSoundOnCorrectAnswer() {
        playSound("/com/example/demo/audio/correctAnswer.mp3");
    }

    public void playSoundOnWinGame() {
        playSound("/com/example/demo/audio/winGame.mp3");
    }


    public void playSound(String fileName) {
        try {
            //System.out.println("ok");
            fileName = getClass().getResource(fileName).toURI().toString();
            Media media = new Media(fileName);
            MediaPlayer player = new MediaPlayer(media);
            wrongAnswerMedia.setMediaPlayer(player);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("ok1");
        wrongAnswerMedia.getMediaPlayer().play();
    }

    public void shutdown() {
        task.cancel();
    }

    public void swap(ArrayList<String> a, int x, int y) {
        String tmp = a.get(x);
        a.set(x, a.get(y));
        a.set(y, tmp);
    }

    public void swap(String[] a, int x, int y) {
        String tmp = a[x];
        a[x] = a[y];
        a[y] = tmp;
    }

    @FXML
    public void restart() {
        //System.out.println("re");
        restartGame = true;
        exportHighScoreToFile();
        loadHighScore();
        restartGame();
    }
    @FXML
    public void onClickButton1() {
        yourAnswer = 1;
        inGame();
    }

    @FXML
    public void onClickButton2() {
        yourAnswer = 2;
        inGame();
    }

    @FXML
    public void onClickButton3() {
        yourAnswer = 3;
        inGame();
    }

    @FXML
    public void onClickButton4() {
        yourAnswer = 4;
        inGame();
    }


    class doWord extends Task<Void> {

    @Override
    protected Void call() throws Exception {
        for (int i = TIME_OF_GAME; i >= 0; i--) {
            if (isCancelled()) {
                updateMessage("Cancelled");
                break;
            }
            if (restartGame) {
                i = TIME_OF_GAME;
                restartGame = false;
            }
            if (checkYourChoose) {
                if (i + 200 < TIME_OF_GAME) {
                    i += 200;
                } else {
                    i = TIME_OF_GAME;
                }
                checkYourChoose = false;
            }
            if (!winGame) {
                updateProgress(i, TIME_OF_GAME);
            }
            //updateProgress(i, TIME_OF_GAME);
            // arr = [10, 20, 30, 40, inf]
            // aaa = [10, 8, 6, 5, 4]
            if (turn < 10) {
                //System.out.println(i);
                Thread.sleep(10);
            } else if (turn < 20) {
                Thread.sleep(8);
            } else if (turn < 30) {
                Thread.sleep(6);
            } else if (turn < 40) {
                Thread.sleep(5);;
            } else {
                Thread.sleep(4);
            }
        }
        lostGame = true;
        lostGame();
        return null;
    }
}
}