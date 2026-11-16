package com.clase.informes;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;

import com.clase.persistencia.ConexionMySQL;

import javafx.scene.control.Alert;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import javafx.scene.control.Alert;


public class imprimirPacientes {


    // =====================================================
    // MÉTIDOD MOSTRAR EL INFORME DE CLIENTES
    // =====================================================
    public static void mostrar() {
    try {
        Connection conexion = ConexionMySQL.getConexion();

        if (conexion == null) {
            throw new Exception("No se pudo conectar con la base de datos.");
        }

        InputStream informe = imprimirPacientes.class.getResourceAsStream(
            "/informes/pacientes.jrxml"
        );

        if (informe == null) {
            throw new Exception("No se encuentra pacientes.jrxml en resources.");
        }

        JasperReport jasperReport =
            JasperCompileManager.compileReport(informe);

        JasperPrint jasperPrint =
            JasperFillManager.fillReport(
                jasperReport,
                new HashMap<>(),
                conexion
            );

        JasperViewer.viewReport(jasperPrint, false);

        conexion.close();

    } catch (Exception e) {
        e.printStackTrace();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error al generar el informe");
        alert.setHeaderText("No se ha podido generar el informe de pacientes");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}
}
