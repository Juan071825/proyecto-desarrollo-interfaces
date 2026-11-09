
import com.clase.persistencia.ConexionMySQL;
import com.clase.persistencia.CitaDAO;
import com.clase.modelo.Cita;

import java.util.List;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CitaDAOMySQL implements CitaDAO {
    
    public void guardarCita(Cita cita) {

        String sql = "INSERT INTO citas "
                    + "(dnipac, iddoc, fechacita, horacita, motivo, diagnostico, tratamiento, estado) " 
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


    public List<Cita> cargarCitas() {

        List<Cita> citas = new ArrayList<>();

            // Solo obtenemos los campos que necesitamos para la tabla
            String sql = "SELECT dnipac, iddoc, fechacita, horacita, motivo, diagnostico, tratamiento, estado "
                    + "FROM citas "
                    + "ORDER BY dnipac, iddoc";

            try (Connection conexion = ConexionMySQL.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            // Recorremos las filas obtenidas
            while (rs.next()) {

                Cita cita = new Cita(
                        rs.getString("dnipac"),
                        rs.getInt("iddoc"),
                        rs.getDate("fechacita").toLocalDate(),
                        rs.getString("horacita"),
                        rs.getString("motivo"),
                        rs.getString("diagnostico"),
                        rs.getString("tratamiento"),
                        rs.getString("estado"));

                citas.add(cita);
            }

             } catch (SQLException e) {
                System.out.println("Error al cargar las citas: " + e.getMessage());
            }

            return citas;
        }


    public void eliminarCita(Integer idcita){

        String sql = "DELETE FROM pacientes WHERE idcita = ?";

        try(Connection conexion = ConexionMySQL.getConexion();
            PreparedStatement ps = conexion.prepareStatement((sql))) {
                ps.setString(1, idcita);

                ps.executeUpdate();

                System.out.println("Cita eliminado correctamente");
            } catch(SQLException e) {
                System.out.println("Error al eliminar el cita: " + e.getMessage());
            }
        }


    public void modificarCita(Integer idcita, Cita cita){

        String sql = "UPDATE citas SET "
                    + "dnipac = ?, iddoc = ?, fechacita = ?, horacita = ?, motivo = ?, diagnostico = ?, tratamiento = ?, estado = ?, idcita = ?"
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
            ps.setInt(9, iddoc);

            ps.executeUpdate();

            System.out.println("Cita modificada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al modificar la cita: " + e.getMessage());
        }
    }

}
    /*Doctor buscaCitaporId(Integer id);*/


