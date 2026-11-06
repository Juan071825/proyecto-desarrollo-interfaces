
package com.clase;

import java.net.URL;
import java.util.ResourceBundle;

import com.clase.modelo.Doctor;
import com.clase.modelo.Paciente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class CitasController implements Initializable {

    @FXML
    private TextField citadnipac, citaiddoc;

    @FXML
    private CheckBox chkatendida, chkpendiente, chkanulada;

    // Recibe el paciente seleccionado
    public void cargarDatosPaciente(Paciente paciente) {
        citadnipac.setText(paciente.getDni());
    }

   public void cargarDatosDoctor(Doctor doctor) {
        // Aquí puedes implementar la lógica para cargar los datos del doctor seleccionado
        // Por ejemplo, podrías mostrar el nombre del doctor en un campo de texto
        // o realizar cualquier otra acción necesaria.
        citaiddoc.setText(String.valueOf(doctor.getIddoc()));
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización de la vista de citas
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
}
