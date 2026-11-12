
package com.clase;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.clase.modelo.Doctor;
import com.clase.modelo.Paciente;
import com.google.gson.JsonObject;
import com.clase.modelo.Cita;
import com.clase.persistencia.CitaDAOMySQL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;


public class CitasController implements Initializable {

    @FXML
    private TextField citadnipac, citaiddoc, idcita, motivo;

    @FXML 
    private DatePicker fechacita;

    @FXML
    private ComboBox<String> horacita;

    @FXML 
    private TextArea diagnostico, tratamiento;

    @FXML
    private CheckBox chkatendida, chkpendiente, chkanulada;

    @FXML
    private TableView<Cita> tablaCitas;

    @FXML 
    private TableColumn<Cita, String> colespedoc, colhoracita, colestado, colapeldoc;

    @FXML 
    private TableColumn<Cita, Integer> colidcita;

    @FXML 
    private TableColumn<Cita, LocalDate> colfechacita;

    @FXML 
    private Label labelCitasPaciente;


    private Boolean citaExiste;


    public void comprobarCargarCitas() {
        if (!citadnipac.getText().isBlank() && !citadnipac.getText().isBlank()) {
            cargarCitasPaciente();
        } else {
            System.out.println("No se cargan las citas del doctor con el paciente por que los campos están vacíos.");
        }
    }

    // Recibe el paciente seleccionado
    public void cargarDatosPaciente(Paciente paciente) {
        limpiarValores();
        citadnipac.setText(paciente.getDni());
        comprobarCargarCitas();
        labelCitasPaciente.setText("Citas Paciente " + paciente.getApellidos() + ", " + paciente.getNombre());
    
    }

        public void limpiarDatosPaciente() {
        citadnipac.setText(null);
        tablaCitas.getItems().clear();
        limpiarValores();
        labelCitasPaciente.setText("Paciente Cita");
    }

   public void cargarDatosDoctor(Doctor doctor) {
        limpiarValores();
        citaiddoc.setText(String.valueOf(doctor.getIddoc()));
        comprobarCargarCitas();
        
    }

    public void limpiarDatosDoctor() {
        citaiddoc.setText(null);
        tablaCitas.getItems().clear();
        limpiarValores();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización de la vista de citas

        cargarHorario();

        colidcita.setCellValueFactory(new PropertyValueFactory<>("idcita"));
        colapeldoc.setCellValueFactory(new PropertyValueFactory<>("apeldoc"));
        colespedoc.setCellValueFactory(new PropertyValueFactory<>("espedoc"));
        colfechacita.setCellValueFactory(new PropertyValueFactory<>("fechacita"));
        colhoracita.setCellValueFactory(new PropertyValueFactory<>("horacita"));
        colestado.setCellValueFactory(new PropertyValueFactory<>("estadocita"));


        tablaCitas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, nuevo) -> {
            if (nuevo != null) {
                cargarCita();
            }

        });


        chkanulada.setOnAction(this::gestionarExclusion);
        chkatendida.setOnAction(this::gestionarExclusion);
        chkpendiente.setOnAction(this::gestionarExclusion);

    }


    private void cargarHorario() {
        
        InputStream is = getClass()
                .getResourceAsStream("/com/clase/data/horario.json");
        
        JsonObject json = JsonParser.parseReader(
                new InputStreamReader(is)).getAsJsonObject();

        JsonArray horas = json.getAsJsonArray("horario");

        for (var hora : horas) {
            horacita.getItems().add(hora.getAsString());
        }
    }
 

    @FXML
    private void gestionarExclusion(ActionEvent event) {
        CheckBox seleccionado = (CheckBox) event.getSource();
        
        if (seleccionado == chkpendiente && chkpendiente.isSelected()) {
            chkatendida.setSelected(false);
            chkanulada.setSelected(false);
        } else if (seleccionado == chkatendida && chkatendida.isSelected()) {
            chkpendiente.setSelected(false);
            chkanulada.setSelected(false);
        } else if (seleccionado == chkanulada && chkanulada.isSelected()) {
            chkpendiente.setSelected(false);
            chkatendida.setSelected(false);
        }
    }

    public void limpiarValores() {

        idcita.setText("");
        //citadnipac.setText("");
        //citaiddoc.setText("");
        chkanulada.setSelected(false);
        chkatendida.setSelected(false);
        chkpendiente.setSelected(false);
        motivo.setText("");
        horacita.getSelectionModel().clearSelection();
        fechacita.setValue(null); 
        diagnostico.setText(null);
        tratamiento.setText(null);
        //labelCitasPaciente.setText("Citas Paciente");
        //tablaCitas.getItems().clear();

    }

    public String getEstado(){

        if (chkanulada.isSelected()){return "Anulada";}
        else if (chkatendida.isSelected()){return "Atendida";}
        else if (chkpendiente.isSelected()){return "Pendiente";}
        else {return null;}
    }


    @FXML
    private void guardarCita() {

        String dni = citadnipac.getText();
        Integer doctor = Integer.valueOf(citaiddoc.getText());

        LocalDate fecha = fechacita.getValue();

        String motivocita = motivo.getText();
        String diagnosticocita = diagnostico.getText();
        String tratamientocita = tratamiento.getText();
        String hora = horacita.getValue();
        String estado = getEstado();


        Cita cita = new Cita(dni, doctor, fecha, hora, motivocita, diagnosticocita, tratamientocita, estado);

        CitaDAOMySQL dao = new CitaDAOMySQL();

        String citaEncontrada = idcita.getText();

        System.out.println("Cita encontrada: " + citaEncontrada);

        if ( !citaEncontrada.isBlank()) {
            System.out.println("Cita encontrada");
            Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
            alert3.setTitle("Aviso");
            alert3.setHeaderText(null);
            alert3.setContentText("¿Desea modificar cita?");
            Optional<ButtonType> result2 = alert3.showAndWait();
            if(result2.isPresent() && result2.get() == ButtonType.OK){
                System.out.println("Botón Aceptar pulsado");
                dao.modificarCita(Integer.valueOf(idcita.getText()), cita);
                System.out.println("Cita modificada: " + cita);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("Cita modificada correctamente");
                alert.showAndWait();
            } else {
                System.out.println("Botón Cancelar pulsado");
                return;
            }
            
        } else {
            System.out.println("Cita no encontrada");
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle("Aviso");
            alert2.setHeaderText(null);
            alert2.setContentText("¿Desea guardar la cita?");
            Optional<ButtonType> result = alert2.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                System.out.println("Botón Aceptar pulsado");
                dao.guardarCita(cita);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("Cita guardada correctamente");
                alert.showAndWait();
            } else {
                System.out.println("Botón Cancelar pulsado");
                return;
            }
        }

        //cargar paciente en la tabla
        cargarCitasPaciente();

    }


    @FXML 
    private void cargarCitasPaciente() {
        CitaDAOMySQL dao = new CitaDAOMySQL();
        List<Cita> citas = dao.cargarCitasPaciente(citadnipac.getText(), citaiddoc.getText());
        tablaCitas.getItems().setAll(citas);
        tablaCitas.refresh();

        System.out.println("Cargar citas: " + citas);
    }


    @FXML 
    private void cargarCita() {
        chkanulada.setSelected(false);
        chkatendida.setSelected(false);
        chkpendiente.setSelected(false);
        Cita citaSelect = tablaCitas.getSelectionModel().getSelectedItem();

        if (citaSelect == null) {
            return;
        } else {
            citaExiste = true;
        }

        CitaDAOMySQL dao = new CitaDAOMySQL();

        Cita cita = dao.buscaCitaporId(citaSelect.getIdcita());

        if (cita == null) {
            return;
        }

        idcita.setText(String.valueOf(cita.getIdcita()));
        citadnipac.setText(cita.getCitadnipac());
        citaiddoc.setText(String.valueOf(cita.getCitaiddoc()));
        motivo.setText(cita.getMotivo());
        fechacita.setValue(cita.getFechacita());
        horacita.setValue(cita.getHoracita());
        tratamiento.setText(cita.getTratamiento());
        diagnostico.setText(cita.getDiagnostico());

        String estadoStr;
        switch (estadoStr = cita.getEstadocita()) {
            
            case "Anulada":
                chkanulada.setSelected(true);
                chkatendida.setSelected(false);
                chkpendiente.setSelected(false);
                break;

            case "Atendida":
                chkanulada.setSelected(false);
                chkatendida.setSelected(true);
                chkpendiente.setSelected(false);
                break;

            case "Pendiente":
                chkanulada.setSelected(false);
                chkatendida.setSelected(false);
                chkpendiente.setSelected(true);
                break;
        
            default:
                chkanulada.setSelected(false);
                chkatendida.setSelected(false);
                chkpendiente.setSelected(false);
                break;
        }
        

        System.out.println("cargar doctor: " + cita);
    }


     @FXML 
    private void eliminarCita() {
        Cita seleccionado = tablaCitas
                .getSelectionModel()
                .getSelectedItem();

        if (seleccionado == null) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText("¿Desea eliminar cita?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("Botón Aceptar pulsado");
            CitaDAOMySQL dao = new CitaDAOMySQL();
            dao.eliminarCita(seleccionado.getIdcita());
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Información");
            alert2.setHeaderText(null);
            alert2.setContentText("Cita eliminada");
            alert2.showAndWait();
        } else {
            System.out.println("Botón Cancelar pulsado");
            return;
        }

        cargarCitasPaciente();
    }

}
