package com.clase;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;


import java.io.IOException;

public class VentanaController {
    
    @FXML 
    private void salirApp(){
        Platform.exit();
    }

    @FXML 
    private void mostrarAcercade() throws IOException {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("com/clase/acercade.fxml")
        );
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("A cerca de");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
