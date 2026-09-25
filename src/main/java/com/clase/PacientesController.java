package com.clase;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;

import com.clase.modelo.Paciente;
import com.clase.persistencia.PacienteDAOMySQL;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PacientesController {

    @FXML
    private TextField dnipac, apelpac, nompac, movilpac, emailpac, dirpac;
    @FXML
    private DatePicker nacpac;
    @FXML
    private ComboBox<String> propac, munipac;
    @FXML
    private Button btnguardarpac, btnmodifpac, btndelpac;

    public void initialize() {

        System.out.println("INITIALIZE EJECUTADO");

        cargarPacientes();

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

        movilpac.focusedProperty().addListener((observable, oldValue, newValue) -> {
        });

        cargarProvincias();
        propac.setOnAction(e -> cargarMunicipios());

    }

    @FXML
    private void comprobarDni() {
        String dni = dnipac.getText().trim().toUpperCase();
        if (dni.isEmpty())
            return;

        if (validarDniNie(dni)) {
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

    private void cargarProvincias() {

        // Abrimos el fichero JSON que está dentro de resources
        // usamos la clase de java InputStrem que lee datos en este caso de un fichero

        InputStream is = getClass()
                .getResourceAsStream("/com/clase/data/municipios.json");

        if (is == null) {
            throw new RuntimeException("================================================NO SE ENCUENTRA EL JSON");
        }

        // Leemos el JSON y lo convertimos en un objeto JsonObject
        JsonObject json = JsonParser.parseReader(
                new InputStreamReader(is)).getAsJsonObject();

        // Obtenemos el array "provincias" del JSON
        JsonArray provincias = json.getAsJsonArray("provincias");

        // Recorremos todas las provincias
        for (var provincia : provincias) {

            // Cada elemento del array es un objeto JSON
            JsonObject p = provincia.getAsJsonObject();

            // Obtenemos el nombre de la provincia
            // y lo añadimos al ComboBox
            propac.getItems().add(
                    p.get("nm").getAsString());
        }
    }

    private void cargarMunicipios() {

        // Abrimos de nuevo el fichero JSON
        InputStream is = getClass()
                .getResourceAsStream("/com/clase/data/municipios.json");

        // Convertimos el contenido del fichero en un JsonObject
        JsonObject json = JsonParser.parseReader(
                new InputStreamReader(is)).getAsJsonObject();

        // Obtenemos los dos arrays que necesitamos
        JsonArray provincias = json.getAsJsonArray("provincias");
        JsonArray municipios = json.getAsJsonArray("municipios");

        // Obtenemos el nombre de la provincia seleccionada
        String nombreProvincia = propac.getValue();

        // Variable donde guardaremos el código de la provincia
        String idProvincia = "";

        // Recorremos las provincias
        for (var provincia : provincias) {

            JsonObject p = provincia.getAsJsonObject();

            // Comprobamos si es la provincia seleccionada
            if (p.get("nm").getAsString().equals(nombreProvincia)) {

                // Obtenemos su código
                idProvincia = p.get("id").getAsString();

                // Ya hemos encontrado la provincia
                break;
            }
        }

        // Eliminamos los municipios que pudiera haber
        // de una selección anterior muy importante sino agrega municipios
        munipac.getItems().clear();

        // Recorremos todos los municipios
        for (var municipio : municipios) {

            JsonObject m = municipio.getAsJsonObject();

            // Comprobamos los dos primeros caracteres del código
            if (m.get("id").getAsString().startsWith(idProvincia)) {

                // Si pertenecen a la provincia,
                // añadimos su nombre al ComboBox
                munipac.getItems().add(
                        m.get("nm").getAsString());
            }
        }
    }

    @FXML
    private void guardarPaciente() {

        if (nacpac.getValue() == null){
            System.out.println("Debes introducir la fecha de nacimiento");
        };

        String dni = dnipac.getText();
        String apellidos = apelpac.getText();
        String nombre = nompac.getText();

        LocalDate fechaNacimiento = nacpac.getValue();

        String movil = movilpac.getText();
        String email = emailpac.getText();
        String direccion = dirpac.getText();

        String provincia = propac.getValue();
        String localidad = munipac.getValue();

        Paciente paciente = new Paciente(dni, apellidos, nombre, movil, email, fechaNacimiento, direccion,  provincia, localidad);
        
        PacienteDAOMySQL dao = new PacienteDAOMySQL();
        dao.guardarPaciente(paciente);

        //cargar paciente en la tabla
        cargarPacientes();


        System.out.println("=====PACIENTE=====");
        System.out.println("DNI: " + dni);
        System.out.println("Apellidos: " + apellidos);
        System.out.println("Nombre: " + nombre);
        System.out.println("Fecha nacimiento: " + fechaNacimiento);
        System.out.println("Teléfono: " + movil);
        System.out.println("Email: " + email);
        System.out.println("Dirección: " + direccion);
        System.out.println("Provincia: " + provincia);
        System.out.println("Localidad: " + localidad);
        System.out.println("==================");
    }

    @FXML 
    private void cargarPacientes() {
        PacienteDAOMySQL dao = new PacienteDAOMySQL();
        List<Paciente> pacientes = dao.cargarPacientes();
        tablaPacientes.getItems().setAll(pacientes);
    }

}
