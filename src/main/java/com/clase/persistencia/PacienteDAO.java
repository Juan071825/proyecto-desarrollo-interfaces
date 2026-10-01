package com.clase.persistencia;
import com.clase.modelo.Paciente;
import java.util.List;

public interface PacienteDAO {
    
    void guardarPaciente(Paciente paciente);
    List<Paciente> cargarPacientes();
    Paciente buscarPaciente(String dni);
    void eliminarPaciente(String dni);
    void modificarPaciente(String dni, Paciente paciente);
}
