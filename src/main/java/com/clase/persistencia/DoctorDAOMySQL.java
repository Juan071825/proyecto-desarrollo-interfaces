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
        String sql = "INSERT INTO pacientes"
                    + "(dnipac, apelpac, nompac, movilpac, emailpac, nacpac, " 
                    + " dirpac, propac, munipac)" 
                    + "VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection conexion = ConexionMySQL.getConexion();
            PreparedStatement ps = conexion.prepareStatement(sql)) {

                ps.setString(1, paciente.getDni());
                ps.setString(2, paciente.getApellidos());
                ps.setString(3, paciente.getNombre());
                ps.setString(4, paciente.getMovil());
                ps.setString(5, paciente.getEmail());
                ps.setDate(6, java.sql.Date.valueOf(paciente.getNacimiento()));
                ps.setString(7, paciente.getDireccion());
                ps.setString(8, paciente.getProvincia());
                ps.setString(9, paciente.getMunicipio());

                ps.executeUpdate();

                System.out.println("Paciente guardado correctamente");

            } catch (SQLException e) {
                System.out.println("Error al guardar el paciente: " + e.getMessage());
            }

    }


    // seleccionar pacientes de la bbdd
    @Override
    public List<Doctor> cargarDoctores() {

        List<Doctor> doctores = new ArrayList<>();

        // Solo obtenemos los campos que necesitamos para la tabla
        String sql = "SELECT dnipac, apelpac, nompac, movilpac, "
                + "propac, munipac "
                + "FROM pacientes "
                + "ORDER BY apelpac, nompac";

        try (Connection conexion = ConexionMySQL.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            // Recorremos las filas obtenidas
            while (rs.next()) {

                Doctor doctor = new Doctor(
                        rs.getString("dnipac"),
                        rs.getString("apelpac"),
                        rs.getString("nompac"),
                        rs.getString("movilpac"),
                        rs.getString("propac"),
                        rs.getString("munipac"));

                doctores.add(doctor);
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar los pacientes: " + e.getMessage());
        }

        return doctores;
    }


     public void  eliminarDoctor(String dni) {

        String sql = "DELETE FROM pacientes WHERE dnipac = ?";

        try(Connection conexion = ConexionMySQL.getConexion();
            PreparedStatement ps = conexion.prepareStatement((sql))) {
                ps.setString(1, dni);

                ps.executeUpdate();

                System.out.println("Paciente eliminado correctamente");
            } catch(SQLException e) {
                System.out.println("Error al eliminar el paciente: " + e.getMessage());
            }

    }


    // Buscar un paciente por DNI
    public Doctor buscarDoctorId(String dni) {

        String sql = "SELECT dnipac, apelpac, nompac, movilpac, "
                + " emailpac, nacpac, dirpac, propac, munipac "
                + " FROM pacientes "
                + " WHERE dnipac = ?";

        try (Connection conexion = ConexionMySQL.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Doctor paciente = new Doctor(
                            rs.getString("dnipac"),
                            rs.getString("apelpac"),
                            rs.getString("nompac"),
                            rs.getString("movilpac"),
                            rs.getString("emailpac"),
                            rs.getDate("nacpac").toLocalDate(),
                            rs.getString("dirpac"),
                            rs.getString("propac"),
                            rs.getString("munipac"));

                    return doctor;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el paciente: " + e.getMessage());
        }

        return null;
    }


    public void modificarPaciente(String dni, Doctor doctor) {

        String sql = "UPDATE pacientes SET "
                + "apelpac = ?, "
                + "nompac = ?, "
                + "movilpac = ?, "
                + "emailpac = ?, "
                + "nacpac = ?, "
                + "dirpac = ?, "
                + "propac = ?, "
                + "munipac = ? "
                + "WHERE dnipac = ?";

        try (Connection conexion = ConexionMySQL.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, doctor.getApellidos());
            ps.setString(2, doctor.getNombre());
            ps.setString(3, doctor.getMovil());
            ps.setString(4, doctor.getEmail());
            ps.setDate(5, java.sql.Date.valueOf(doctor.getNacimiento()));
            ps.setString(6, doctor.getDireccion());
            ps.setString(7, doctor.getProvincia());
            ps.setString(8, doctor.getMunicipio());

            // DNI original para localizar el paciente
            ps.setString(9, dni);

            ps.executeUpdate();

            System.out.println("Paciente modificado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al modificar el paciente: " + e.getMessage());
        }
    }


     public Doctor buscaPacdni(String dni) {

        String sql = "SELECT dnipac, apelpac, nompac, movilpac, "
                + " emailpac, nacpac, dirpac, propac, munipac "
                + " FROM pacientes "
                + " WHERE dnipac = ?";

        try (Connection conexion = ConexionMySQL.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Doctor paciente = new Doctor(
                            rs.getString("dnipac"),
                            rs.getString("apelpac"),
                            rs.getString("nompac"),
                            rs.getString("movilpac"),
                            rs.getString("emailpac"),
                            rs.getDate("nacpac").toLocalDate(),
                            rs.getString("dirpac"),
                            rs.getString("propac"),
                            rs.getString("munipac"));

                    return paciente;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el paciente: " + e.getMessage());
        }

        return null;
    }
}

