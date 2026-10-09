package com.clase.persistencia;
import com.clase.modelo.Doctor;
import java.util.List;

public interface DoctorDAO {
    
    void guardarDoctor(Doctor doctor);
    List<Doctor> cargarDoctores();
    /*Doctor buscarDoctor(Integer id);
    void eliminarDoctor(Integer id);
    void modificarDoctor(Integer id, Doctor paciente); */
    Doctor buscaDocId(String id);
}