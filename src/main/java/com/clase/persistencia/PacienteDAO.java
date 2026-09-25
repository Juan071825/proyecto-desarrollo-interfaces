package com.clase.persistencia;
import com.clase.modelo.Paciente;
import java.util.List;

public interface PacienteDAO {
    
    void guardarPaciente(Paciente paciente);
    List<Paciente> cargarPacientes();

}
