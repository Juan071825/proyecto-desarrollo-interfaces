package com.clase.persistencia;

import com.clase.persistencia.ConexionMySQL;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import com.clase.persistencia.CitaDAO;
import com.clase.modelo.Cita;
import com.clase.modelo.Doctor;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CitaDAOMySQL implements CitaDAO {
    
    public void guardarCita(Cita cita) {

        String sql = "INSERT INTO citas "
                    + "(citadnipac, citaiddoc, fechacita, horacita, motivo, diagnostico, tratamiento, estadocita) " 
                    + "VALUES (?,?,?,?,?,?,?,?)";

        try (Connection conexion = ConexionMySQL.getConexion();
            PreparedStatement ps = conexion.prepareStatement(sql)) {

                ps.setString(1, cita.getCitadnipac());
                ps.setString(2, String.valueOf(cita.getCitaiddoc()));
                ps.setDate(3, java.sql.Date.valueOf(cita.getFechacita()));
                ps.setString(4, cita.getHoracita());
                ps.setString(5, cita.getMotivo());
                ps.setString(6, cita.getDiagnostico());
                ps.setString(7, cita.getTratamiento());
                ps.setString(8, cita.getEstadocita());

                ps.executeUpdate();

                System.out.println("Cita guardada correctamente");

            } catch (SQLException e) {
                System.out.println("Error al guardar la cita: " + e.getMessage());
            }
    }


    public List<Cita> cargarCitasPaciente(String dnipac, String iddoc) {

        List<Cita> citas = new ArrayList<>();

            // Solo obtenemos los campos que necesitamos para la tabla
           String sql = "SELECT c.idcita, c.citadnipac, c.citaiddoc, "
                        + "c.fechacita, c.horacita, c.motivo, "
                        + "c.diagnostico, c.tratamiento, c.estadocita, "
                        + "d.apeldoc, d.espedoc "
                        + "FROM citas c "
                        + "INNER JOIN doctores d ON c.citaiddoc = d.iddoc "
                        + "WHERE c.citadnipac = ? AND c.citaiddoc = ?"
                        + "ORDER BY c.fechacita";

            try (Connection conexion = ConexionMySQL.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)) {
                    ps.setString(1, dnipac);
                    ps.setString(2, iddoc);
                    try (ResultSet rs = ps.executeQuery()){
                        // Recorremos las filas obtenidas
                        while (rs.next()) {
                            Cita cita = new Cita(
                                    rs.getInt("idcita"),
                                    rs.getInt("citaiddoc"),
                                    rs.getDate("fechacita").toLocalDate(),
                                    rs.getString("espedoc"),
                                    rs.getString("horacita"),
                                    rs.getString("estadocita"));

                            citas.add(cita);
                        }
                    }

                } catch (SQLException e) {
                    System.out.println("Error al cargar las citas: " + e.getMessage());
                }

            return citas;
        }


    public void eliminarCita(Integer idcita){

        String sql = "DELETE FROM citas WHERE idcita = ?";

        try(Connection conexion = ConexionMySQL.getConexion();
            PreparedStatement ps = conexion.prepareStatement((sql))) {
                ps.setString(1, String.valueOf(idcita));

                ps.executeUpdate();

                System.out.println("Cita eliminado correctamente");
            } catch(SQLException e) {
                System.out.println("Error al eliminar el cita: " + e.getMessage());
            }
        }


    public void modificarCita(Integer idcita, Cita cita){

        String sql = "UPDATE citas SET "
                    + "citadnipac = ?, citaiddoc = ?, fechacita = ?, horacita = ?, motivo = ?, diagnostico = ?, tratamiento = ?, estadocita = ?"
                    + "WHERE idcita = ?";

        try (Connection conexion = ConexionMySQL.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)) {

                ps.setString(1, cita.getCitadnipac());
                ps.setString(2, String.valueOf(cita.getCitaiddoc()));
                ps.setDate(3, java.sql.Date.valueOf(cita.getFechacita()));
                ps.setString(4, cita.getHoracita());
                ps.setString(5, cita.getMotivo());
                ps.setString(6, cita.getDiagnostico());
                ps.setString(7, cita.getTratamiento());
                ps.setString(8, cita.getEstadocita());
            
            System.out.println(idcita);
            ps.setInt(9, idcita);

            ps.executeUpdate();

            System.out.println("Cita modificada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al modificar la cita: " + e.getMessage());
        }
    }


    public Cita buscaCitaporId(Integer id){

         String sql = "SELECT idcita, citadnipac, citaiddoc, fechacita, horacita, motivo, diagnostico,tratamiento, estadocita"
                        + " FROM citas "
                        + " WHERE idcita = ?";

        try (Connection conexion = ConexionMySQL.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Cita cita = new Cita(
                        rs.getInt("idcita"),
                        rs.getString("citadnipac"),
                        rs.getInt("citaiddoc"),
                        rs.getDate("fechacita").toLocalDate(),
                        rs.getString("horacita"),
                        rs.getString("motivo"),
                        rs.getString("diagnostico"),
                        rs.getString("tratamiento"),
                        rs.getString("estadocita")
                    );

                    return cita;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el cita: " + e.getMessage());
        }

        return null;

    }

}
