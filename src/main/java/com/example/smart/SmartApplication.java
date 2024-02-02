package com.example.smart;

import com.example.smart.config.FileConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SmartApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FileConfig.createIniFile();
        FileConfig.createLogFile();

        FXMLLoader fxmlLoader = new FXMLLoader(SmartApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("DATA R&D");

        stage.setMinHeight(760);
        stage.setMinWidth(1170);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}