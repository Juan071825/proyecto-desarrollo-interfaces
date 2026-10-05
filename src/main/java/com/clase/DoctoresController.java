package com.clase;

import com.clase.modelo.Doctor;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;

import com.clase.modelo.Paciente;
import com.google.gson.JsonObject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class DoctoresController {

    @FXML
    private TextField iddoc, apeldoc, nomdoc, movildoc, maildoc;

    @FXML
    private ComboBox<String> espedoc;
    @FXML
    private Button btnguardarpac, btndelpac, btnlimpiarpac;

    @FXML
    private TableView<Doctor> tablaDoctores;

    @FXML 
    private TableColumn<Paciente, String> coliddoc, colapeldoc, colnomdoc, colmovildoc, colespedoc;

        boolean doctorExiste = false;
    
    public void initialize() {

        System.out.println("INITIALIZE EJECUTADO");

        nomdoc.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String nombre = ponerInicialesMayusculas(nomdoc.getText());
                nomdoc.setText(nombre);
            }

        });

        apeldoc.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String apellidos = ponerInicialesMayusculas(apeldoc.getText());
                apeldoc.setText(apellidos);
            }
        });

        movildoc.focusedProperty().addListener((observable, oldValue, newValue) -> {
            comprobarMovil();
        });

        maildoc.focusedProperty().addListener((observable, oldValue, newValue) -> {
            comprobarEmail();
        });

        cargarEspecialidades();
        espedoc.setOnAction(e -> cargarEspecialidades());
        

        coliddoc.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colapeldoc.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colnomdoc.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colmovildoc.setCellValueFactory(new PropertyValueFactory<>("movil"));
        colespedoc.setCellValueFactory(new PropertyValueFactory<>("especialidades"));

        tablaDoctores.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, nuevo) -> {

            if (nuevo != null) {
                cargarDoctor();
            }

        });

    }





      private void cargarEspecialidades() {

        // Abrimos el fichero JSON que está dentro de resources
        // usamos la clase de java InputStrem que lee datos en este caso de un fichero

        InputStream is = getClass()
                .getResourceAsStream("/com/clase/data/especialidades.json");

        if (is == null) {
            throw new RuntimeException("================================================NO SE ENCUENTRA EL JSON");
        }

        // Leemos el JSON y lo convertimos en un objeto JsonObject
        JsonObject json = JsonParser.parseReader(
                new InputStreamReader(is)).getAsJsonObject();

        // Obtenemos el array "provincias" del JSON
        JsonArray especialidades = json.getAsJsonArray("provincias");

        // Recorremos todas las provincias
        for (var especialidad : especialidades) {

            // Cada elemento del array es un objeto JSON
            JsonObject p = especialidad.getAsJsonObject();

            // Obtenemos el nombre de la provincia
            // y lo añadimos al ComboBox
            espedoc.getItems().add(
                    p.get("nombre").getAsString());
        }
    }


    @FXML 
    private void limpiarValores() {
        iddoc.setText("");
        apeldoc.setText("");
        nomdoc.setText("");
        movildoc.setText("");
        maildoc.setText("");
        espedoc.getSelectionModel().clearSelection();
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
    private void comprobarMovil() {
        String movil = movildoc.getText().trim().toUpperCase();
        if (movil.isEmpty())
            return;

        if (validarMovil(movil)) {
            movildoc.setStyle("");
            movildoc.setStyle(movil);
        } else {
            movildoc.setStyle("-fx-border-color : red;");
            movildoc.setText("");
        }
    }


    @FXML
    private boolean validarMovil(String movil) {
        return movil.matches("[67][0-9]{8}");
    }


    private void comprobarEmail(){
        String mail = maildoc.getText().trim().toUpperCase();
        if (mail.isEmpty())
            return;

        if (validarEmail(mail)) {
            maildoc.setStyle("");
            maildoc.setStyle(mail);
        } else {
            maildoc.setStyle("-fx-border-color : red;");
            maildoc.setText("");
        }
    }


    private boolean validarEmail(String mail) {
        if (mail == null || mail.isBlank()) {
            return false;
        }

        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        return mail.matches(regex);
    }


    @FXML
    private void guardarPaciente() {

        String dni = iddoc.getText();
        String apellidos = apeldoc.getText();
        String nombre = nomdoc.getText();
        String movil = movildoc.getText();
        String mail = maildoc.getText();
        String especialidad = espedoc.getValue();


        Paciente paciente = new Paciente(dni, apellidos, nombre, movil, mail,  especialidad);        

        if (doctorExiste) {
            PacienteDAOMySQL dao = new PacienteDAOMySQL();
            dao.modificarPaciente(paciente.getDni(), paciente);
            doctorExiste = false;
        } else {
            PacienteDAOMySQL dao = new PacienteDAOMySQL();
            dao.guardarPaciente(paciente);
        }
    }


    @FXML 
    private void cargarDoctores() {
        PacienteDAOMySQL dao = new DoctorDAOMySQL();
        List<Doctor> doctores = dao.cargarDoctores();
        tablaDoctores.getItems().setAll(doctores);
    }
}
