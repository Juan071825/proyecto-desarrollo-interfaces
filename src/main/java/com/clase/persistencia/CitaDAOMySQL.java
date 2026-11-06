public class CitaDAOMySQL implements CitaDAO {
    
    void guardarCita(Cita cita) {

        String sql = "INSERT INTO citas "
                    + "(dnipac, iddoc, fechacita, horacita, motivo, diagnostico, tratamiento, estado) " 
                    + "VALUES (?,?,?,?,?,?,?,?)";

        try (Connection conexion = ConexionMySQL.getConexion();
            PreparedStatement ps = conexion.prepareStatement(sql)) {

                ps.setString(1, cita.getCitadnipac());
                ps.setString(2, String.parseInt(cita.getCitaiddoc()));
                ps.setDate(3, java.sql.Date.valueOf(cita.getFechacita()));
                ps.setString(4, cita.getHoracita());
                ps.setString(5, cita.getMotivo());
                ps.setString(6, cita.getDiagnostico());
                ps.setString(7, cita.getTratamiento());
                ps.setString(8, cita.getEstadocita())

                ps.executeUpdate();

                System.out.println("Cita guardada correctamente");

            } catch (SQLException e) {
                System.out.println("Error al guardar la cita: " + e.getMessage());
            }
    }


    List<Cita> cargarCitas() {

        List<Paciente> citas = new ArrayList<>();

            // Solo obtenemos los campos que necesitamos para la tabla
            String sql = "SELECT dnipac, iddoc, fechacita, horacita, motivo, diagnostico, tratamiento, estado "
                    + "FROM citas "
                    + "ORDER BY dnipac, iddoc";

            try (Connection conexion = ConexionMySQL.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            // Recorremos las filas obtenidas
            while (rs.next()) {

                Paciente paciente = new Paciente(
                        rs.getString("dnipac"),
                        rs.getString("iddoc"),
                        rs.getString("nompac"),
                        rs.getString("movilpac"),
                        rs.getString("propac"),
                        rs.getString("munipac"));

                pacientes.add(paciente);
            }

             } catch (SQLException e) {
                System.out.println("Error al cargar los pacientes: " + e.getMessage());
            }

            return pacientes;
        }

    }



    void eliminarCita(Integer id);
    void modificarCita(Integer id, Cita cita);
    Doctor buscaCitaporId(Integer id);

}
