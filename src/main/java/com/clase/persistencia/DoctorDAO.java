package com.clase.persistencia;
import com.clase.modelo.Doctor;
import java.util.List;

public interface DoctorDAO {
    
    void guardarDoctor(Doctor doctor);
    List<Doctor> cargarDoctores();
    Doctor buscarDoctor(String id);
    void eliminarDoctor(String id);
    void modificarDoctor(String id, Doctor paciente);
    Doctor buscaDocId(String id);
}