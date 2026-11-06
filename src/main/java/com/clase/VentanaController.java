package com.clase;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import com.clase.PacientesController;


import java.io.IOException;

public class VentanaController {

    @FXML
    private PacientesController panelPacientesController;

    @FXML
    private DoctoresController panelDoctoresController;

    @FXML
    private CitasController panelCitasController;

    @FXML
    public void initialize() {

        //para acceder desde pacientes y doctores al panel citas
        panelPacientesController.setCitasController(panelCitasController);
        panelDoctoresController.setCitasController(panelCitasController);

    }

    
    @FXML 
    private void salirApp(){
        Platform.exit();
    }

    @FXML 
    private void mostrarAcercade() throws IOException {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/com/clase/acercade.fxml")
        );
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("A cerca de");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML 
    private void abrirDir() {
        String so = System.getProperty("os.name").toLowerCase();
        String carpeta = System.getProperty("user.home");
        try {
            if (so.contains("win")){
                new ProcessBuilder("explorer.exe").start();
                System.out.println("Explorer de Windows abierto.");
            } else {
                new ProcessBuilder("xdg-open", carpeta).start();
                System.out.println("XPG de Linux abierto.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
