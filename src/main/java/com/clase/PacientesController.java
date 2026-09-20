package com.clase;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;


public class PacientesController {
    
    @FXML 
    private TextField dnipac, apelpac, nompac, tlfpac, emailpac, dirpac;
    @FXML 
    private DatePicker nacpac;
    @FXML 
    private ComboBox<String> propac, locpac;
    @FXML 
    private Button btnguardarpac, btnmodifpac, btndelpac;

    public void initialize() {

        System.out.println("INITIALIZE EJECUTADO");

        dnipac.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                comprobarDni();
            }

        });

        nompac.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String nombre = ponerInicialesMayusculas(nompac.getText());
                nompac.setText(nombre);
            }
            
        });

        apelpac.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String apellidos = ponerInicialesMayusculas(apelpac.getText());
                nompac.setText(apellidos);
            }
        });


        tlfpac.focusedProperty().addListener((observable, oldValue, newValue) -> {});



        propac.getItems().addAll(
            "A Coruña",
            "Lugo",
            "Ourense",
            "Pontevedra"
        );

    }

    @FXML 
    private void comprobarDni(){
        String dni = dnipac.getText().trim().toUpperCase();
        if (dni.isEmpty())
            return;

        if(validarDniNie(dni)) {
            dnipac.setStyle("");
            dnipac.setStyle(dni);
        } else {
            dnipac.setStyle("-fx-border-color : red;");
            dnipac.setText("");
        }
    }

    private boolean validarDniNie(String documento) {

        if (documento.matches("\\d{8}[A-Z]")) {

            int numero = Integer.parseInt(documento.substring(0, 8));
            char letra = "TRWAGMYFPDXBNJZSQVHLCKE".charAt(numero % 23);

            return letra == documento.charAt(8);
        }

        if (documento.matches("[XYZ]\\d{7}[A-Z]")) {

            String nie = documento
                    .replace("X", "0")
                    .replace("Y", "1")
                    .replace("Z", "2");

            int numero = Integer.parseInt(nie.substring(0, 8));
            char letra = "TRWAGMYFPDXBNJZSQVHLCKE".charAt(numero % 23);

            return letra == documento.charAt(8);
        }

        return false;
    }

    @FXML 
    private String ponerInicialesMayusculas(String texto) {
        String[] palabras = texto.toLowerCase().trim().split("\\s+");
        StringBuilder resultado = new StringBuilder();

        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                resultado.append(Character.toUpperCase(palabra.charAt(0)))
                         .append(palabra.substring(1))
                         .append(" ");
            }
        }

        return resultado.toString().trim();
    };

    @FXML
    private boolean validarTelefono(String telefono) {
        return telefono.matches("[67][0-9]{8}");
    }

    @FXML 
    private void guardarPaciente() {

        String dni = dnipac.getText();
        String apellidos = apelpac.getText();
        String nombre = nompac.getText();

        LocalDate fechaNacimiento = nacpac.getValue();

        String telefono = tlfpac.getText();
        String email = emailpac.getText();
        String direccion = dirpac.getText();

        String provincia = propac.getValue();
        String localidad = locpac.getValue();

        System.out.println("=====PACIENTE=====");
        System.out.println("DNI: " + dni);
        System.out.println("Apellidos: " + apellidos);
        System.out.println("Nombre: " + nombre);
        System.out.println("Fecha nacimiento: " + fechaNacimiento);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Email: " + email);
        System.out.println("Dirección: " + direccion);
        System.out.println("Provincia: " + provincia);
        System.out.println("Localidad: " + localidad);
        System.out.println("==================");        
    }


}
