package com.clase;

import com.clase.modelo.Doctor;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.clase.modelo.Paciente;
import com.clase.persistencia.DoctorDAOMySQL;
import com.google.gson.JsonObject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.net.URL;



public class DoctoresController implements Initializable {

    @FXML
    private TextField iddoc, apeldoc, nomdoc, movildoc, emaildoc;

    @FXML
    private ComboBox<String> espedoc;
    @FXML
    private Button btnguardardoc, btndeldoc, btnlimpiardoc;

    @FXML 
    private RadioButton coledocsi, coledocno;

    @FXML
    private TableView<Doctor> tablaDoctores;

    @FXML 
    private TableColumn<Paciente, String> coliddoc, colapeldoc, colnomdoc, colmovildoc, colespedoc;

        boolean doctorExiste = false;
    
    public void initialize(URL url, ResourceBundle rb) {

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

        emaildoc.focusedProperty().addListener((observable, oldValue, newValue) -> {
            comprobarEmail();
        });

        cargarEspecialidades();
        espedoc.setOnAction(e -> cargarEspecialidades());
        

        coliddoc.setCellValueFactory(new PropertyValueFactory<>("iddoc"));
        colapeldoc.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colnomdoc.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colmovildoc.setCellValueFactory(new PropertyValueFactory<>("movil"));
        colespedoc.setCellValueFactory(new PropertyValueFactory<>("especialidad"));

        tablaDoctores.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, nuevo) -> {

            if (nuevo != null) {
                cargarDoctor();
            }

        });

        

        cargarDoctores();

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
        JsonArray especialidades = json.getAsJsonArray("especialidades");

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
        emaildoc.setText("");
        espedoc.getSelectionModel().clearSelection();
        coledocno.setSelected(false);
        coledocsi.setSelected(false);
        doctorExiste = false;
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
        String email = emaildoc.getText().trim().toUpperCase();
        if (email.isEmpty())
            return;

        if (validarEmail(email)) {
            emaildoc.setStyle("");
            emaildoc.setStyle(email);
        } else {
            emaildoc.setStyle("-fx-border-color : red;");
            emaildoc.setText("");
        }
    }


    private boolean validarEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }

        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        return email.matches(regex);
    }


    @FXML
    private void guardarDoctor() {

        String id = iddoc.getText(); 
        String apellidos = apeldoc.getText();
        String nombre = nomdoc.getText();
        String movil = movildoc.getText();
        String email = emaildoc.getText();
        Boolean colegiado = coledocsi.isSelected()? true : false;
        String especialidad = espedoc.getValue();


        Doctor doctor = new Doctor(apellidos, nombre, colegiado, movil, email, especialidad);      

        boolean modificando = !iddoc.getText().isBlank();

        if (modificando) {
            Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
            alert3.setTitle("Aviso");
            alert3.setHeaderText(null);
            alert3.setContentText("¿Desea modificar doctor?");
            Optional<ButtonType> result2 = alert3.showAndWait();
            if(result2.isPresent() && result2.get() == ButtonType.OK){
                DoctorDAOMySQL dao = new DoctorDAOMySQL();
                dao.modificarDoctor(Integer.parseInt(id), doctor);
                Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
                alert4.setTitle("Información");
                alert4.setHeaderText(null);
                alert4.setContentText("Doctor modificado");
                alert4.showAndWait();
            } else {
                return;
            }
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("¿Desea guardar doctor?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                DoctorDAOMySQL dao = new DoctorDAOMySQL();
                dao.guardarDoctor(doctor);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Aviso");
                alert2.setHeaderText(null);
                alert2.setContentText("Doctor guardado");
                alert2.showAndWait();
            } else {
                return;
            }
        }
        cargarDoctores();
    }


    @FXML 
    private void cargarDoctores() {
        DoctorDAOMySQL dao = new DoctorDAOMySQL();
        List<Doctor> doctores = dao.cargarDoctores();
        tablaDoctores.getItems().setAll(doctores);
    }

    @FXML 
    private void cargarDoctor() {
        coledocno.setSelected(false);
        coledocsi.setSelected(false);
        Doctor doctorSelect = tablaDoctores.getSelectionModel().getSelectedItem();

        if (doctorSelect == null) {
            return;
        } else {
            doctorExiste = true;
        }

        DoctorDAOMySQL dao = new DoctorDAOMySQL();

        Doctor doctor = dao.buscaDocporId(doctorSelect.getIddoc());

        if (doctor == null) {
            return;
        }

        iddoc.setText(Integer.toString(doctor.getIddoc()));
        apeldoc.setText(doctor.getApellidos());
        nomdoc.setText(doctor.getNombre());
        movildoc.setText(doctor.getMovil());
        emaildoc.setText(doctor.getEmail());
        if (doctor.getColegiado() == true){
            coledocsi.setSelected(true);
        } else {
            coledocno.setSelected(true);
        }
        espedoc.setValue(doctor.getEspecialidad());

    }


    @FXML 
    private void eliminarDoctor() {
        Doctor seleccionado = tablaDoctores
                .getSelectionModel()
                .getSelectedItem();

        if (seleccionado == null) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText("¿Desea eliminar doctor?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Información");
            alert2.setHeaderText(null);
            alert2.setContentText("Doctor eliminado");
            alert2.showAndWait();
            DoctorDAOMySQL dao = new DoctorDAOMySQL();
            dao.eliminarDoctor(seleccionado.getIddoc());
        } else {
            return;
        }

        cargarDoctores();
    }
    

        @FXML
    private void buscadocsporEspecialidad(){

        DoctorDAOMySQL dao = new DoctorDAOMySQL();
        List<Doctor> Doctores = dao.cargarDoctoresEspec(espedoc.getValue());
        tablaDoctores.getItems().setAll(Doctores);

    }
}
