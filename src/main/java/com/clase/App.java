package com.clase;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/com/clase/ventana.fxml")
        );

            Scene scene = new Scene(loader.load(), 1024, 800);
                stage.setTitle("Sanitarios Teis");
                stage.setScene(scene);
                stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}