package com.example.demo;

import com.example.demo.BasePlus.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/demo/fxml/container.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("BAB_More than a dictionary");
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/demo/logo/B.png")));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}