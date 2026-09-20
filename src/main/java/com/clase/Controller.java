package com.clase;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {

    @FXML private TextField nombre;
    @FXML private ComboBox<String> combo;
    @FXML private RadioButton r1;
    @FXML private RadioButton r2;
    @FXML private CheckBox c1;
    @FXML private CheckBox c2;
    @FXML private CheckBox c3;

    @FXML
    private void inicializate(){
        combo.getItems().addAll("Rojo", "Verde", "Azul");
    }

    @FXML
    private void mostrar(){
        
        String genero = 
            r1.isSelected() ? "Hombre":
            r2.isSelected() ? "Mujer":
            "No seleccionado";

        String intereses = "";
        if (c1.isSelected()) intereses += "Deporte";
        if (c2.isSelected()) intereses += "Música";
        if (c3.isSelected()) intereses += "Viajes";

        System.out.println("Nombre: " + nombre.getText());
        System.out.println("Color: " + combo.getValue());
        System.out.println("Género: " + genero);
        System.out.println("Intereses " + intereses);


    }


}