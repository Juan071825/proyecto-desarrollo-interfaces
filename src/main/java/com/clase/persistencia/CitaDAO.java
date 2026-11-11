package com.clase.persistencia;

import com.clase.modelo.Cita;
import com.clase.modelo.Doctor;

import java.util.List;


public interface CitaDAO {
    
    public void guardarCita(Cita cita);
    public List<Cita> cargarCitasPaciente(String dnipac, String iddoc);
    public void eliminarCita(Integer idcita);
    public void modificarCita(Integer idcita, Cita cita);
    /*Doctor buscaCitaporId(Integer id);*/

}
