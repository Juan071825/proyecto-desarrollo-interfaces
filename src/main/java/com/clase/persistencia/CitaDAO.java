public interface CitaDAO {
    
    void guardarCita(Cita cita);
    List<Cita> cargarCitas();
    void eliminarCita(Integer id);
    void modificarCita(Integer id, Cita cita);
    Doctor buscaCitaporId(Integer id);

}
