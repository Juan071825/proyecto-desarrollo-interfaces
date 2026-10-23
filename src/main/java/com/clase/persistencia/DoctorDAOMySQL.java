package com.clase.persistencia;

import com.clase.modelo.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class DoctorDAOMySQL implements DoctorDAO {
    
    @Override 
    public void guardarDoctor(Doctor doctor) {
        String sql = "INSERT INTO doctores "
                    + "(apeldoc, nomdoc, movildoc, emaildoc, coledoc, espedoc) " 
                    + "VALUES (?,?,?,?,?,?)";

        try (Connection conexion = ConexionMySQL.getConexion();
            PreparedStatement ps = conexion.prepareStatement(sql)) {

                ps.setString(1, doctor.getApellidos());
                ps.setString(2, doctor.getNombre());
                ps.setString(3, doctor.getMovil());
                ps.setString(4, doctor.getEmail());
                ps.setBoolean(5, doctor.getColegiado());
                ps.setString(6, doctor.getEspecialidad());


                ps.executeUpdate();

                System.out.println("Doctor guardado correctamente");

            } catch (SQLException e) {
                System.out.println("Error al guardar el doctor: " + e.getMessage());
            }
    }


    // seleccionar doctores de la bbdd
    @Override
    public List<Doctor> cargarDoctores() {

        List<Doctor> doctores = new ArrayList<>();

        String sql = "SELECT * "
                + "FROM doctores "
                + "ORDER BY apeldoc, nomdoc";

        try (Connection conexion = ConexionMySQL.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            // Recorremos las filas obtenidas
            while (rs.next()) {

                Doctor doctor = new Doctor(
                        rs.getInt("iddoc"),
                        rs.getString("apeldoc"),                        
                        rs.getString("nomdoc"),
                        rs.getString("movildoc"),
                        rs.getString("espedoc"));

                doctores.add(doctor);
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar los doctores: " + e.getMessage());
        }

        return doctores;
    }


    public void  eliminarDoctor(Integer id) {

        String sql = "DELETE FROM doctores WHERE iddoc = ?";

        try(Connection conexion = ConexionMySQL.getConexion();
            PreparedStatement ps = conexion.prepareStatement((sql))) {
                ps.setInt(1, id);

                ps.executeUpdate();

                System.out.println("Doctor eliminado correctamente");

            } catch(SQLException e) {
                System.out.println("Error al eliminar doctor: " + e.getMessage());
            }
    }


    // Buscar un paciente por DNI
    public Doctor buscaDocporId(Integer id) {

        String sql = "SELECT iddoc, apeldoc, nomdoc, coledoc, movildoc, "
                + " emaildoc, espedoc "
                + " FROM doctores "
                + " WHERE iddoc = ?";

        try (Connection conexion = ConexionMySQL.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Doctor doctor = new Doctor(
                            rs.getInt("iddoc"),
                            rs.getString("apeldoc"),
                            rs.getString("nomdoc"),
                            rs.getBoolean("coledoc"),
                            rs.getString("movildoc"),
                            rs.getString("emaildoc"),
                            rs.getString("espedoc")
                        );

                    return doctor;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el doctor: " + e.getMessage());
        }

        return null;
    }


    public void modificarDoctor(Integer iddoc, Doctor doctor) {

        String sql = "UPDATE doctores SET "
                + "apeldoc = ?, "
                + "nomdoc = ?, "
                + "coledoc = ?, "
                + "movildoc = ?, "
                + "emaildoc = ?, "
                + "espedoc = ? "
                + "WHERE iddoc = ?";

        try (Connection conexion = ConexionMySQL.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, doctor.getApellidos());
            ps.setString(2, doctor.getNombre());
            ps.setBoolean(3,doctor.getColegiado() != null && doctor.getColegiado());
            ps.setString(4, doctor.getMovil());
            ps.setString(5, doctor.getEmail());
            ps.setString(6, doctor.getEspecialidad());
            

            // DNI original para localizar el paciente
            System.out.println(iddoc);
            ps.setInt(7, iddoc);

            ps.executeUpdate();

            System.out.println("Doctor modificado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al modificar el doctor: " + e.getMessage());
        }
    }


     /*public Doctor buscaPacdni(String dni) {

        String sql = "SELECT dnipac, apelpac, nompac, movilpac, "
                + " emailpac, nacpac, dirpac, propac, munipac "
                + " FROM pacientes "
                + " WHERE dnipac = ?";

        try (Connection conexion = ConexionMySQL.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Doctor doctor = new Doctor(
                            rs.getInt("iddoc"),
                            rs.getString("apeldoc"),
                            rs.getString("nomdoc"),
                            rs.getBoolean("coledoc"),
                            rs.getString("movildoc"),
                            rs.getString("maildoc"),
                            rs.getString("espedoc"));

                    return doctor;
                }
            } 

        } catch (SQLException e) {
            System.out.println("Error al buscar el doctor: " + e.getMessage());
        }

        return null;
    } */


    public List<Doctor> cargarDoctoresEspec(String espedoc){
        List<Doctor> doctores = new ArrayList<>();
         String sql = "SELECT iddoc, nomdoc, apeldoc, coledoc, movildoc, "
                + " emaildoc, espedoc "
                + " FROM doctores "
                + " WHERE espedoc = ?";

        try (Connection conexion = ConexionMySQL.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, espedoc);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Doctor doctor = new Doctor(
                            rs.getInt("iddoc"),
                            rs.getString("nomdoc"),
                            rs.getString("apeldoc"),
                            rs.getBoolean("coledoc"),
                            rs.getString("movildoc"),
                            rs.getString("emaildoc"),
                            rs.getString("espedoc"));

                    doctores.add(doctor);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el doctor: " + e.getMessage());
        }
        System.out.println("Doctores por especialidad: " + doctores);
        return doctores;
    }
}

