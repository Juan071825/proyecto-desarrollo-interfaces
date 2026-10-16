package com.clase.persistencia;
import com.clase.modelo.Doctor;
import java.util.List;

public interface DoctorDAO {
    
    void guardarDoctor(Doctor doctor);
    List<Doctor> cargarDoctores();
    List<Doctor> cargarDoctoresEspec(String espedoc);
    void eliminarDoctor(Integer id);
    void modificarDoctor(Integer id, Doctor paciente);
    Doctor buscaDocporId(Integer id);
}