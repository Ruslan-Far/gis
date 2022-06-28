package com.ruslan.gis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("primary_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("ГИС");
        primaryStage.setScene(scene);

        PrimaryController.primaryStage = primaryStage;

        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}