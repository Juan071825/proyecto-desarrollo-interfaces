
package com.clase.modelo;

import java.time.LocalDate;

public class Cita {

    private Integer idcita;
    private String citadnipac;
    private Integer citaiddoc;
    private LocalDate fechacita;
    private String horacita;
    private String motivo;
    private String diagnostico;
    private String tratamiento;
    private String estadocita;

    private String espedoc;

    // Constructor vacío
    public Cita() {
    }

    // Constructor con todos los campos
    public Cita(Integer idcita, String citadnipac, Integer citaiddoc,
                LocalDate fechacita, String horacita,
                String motivo, String diagnostico,
                String tratamiento, String estadocita) {

        this.idcita = idcita;
        this.citadnipac = citadnipac;
        this.citaiddoc = citaiddoc;
        this.fechacita = fechacita;
        this.horacita = horacita;
        this.motivo = motivo;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.estadocita = estadocita;
    }


        public Cita(String citadnipac, Integer citaiddoc,
                LocalDate fechacita, String horacita,
                String motivo, String diagnostico,
                String tratamiento, String estadocita) {

        this.citadnipac = citadnipac;
        this.citaiddoc = citaiddoc;
        this.fechacita = fechacita;
        this.horacita = horacita;
        this.motivo = motivo;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.estadocita = estadocita;
    }

    public Cita(Integer idcita, Integer citaiddoc,
                LocalDate fechacita, String espedoc, 
                String horacita, String estadocita) {

        this.idcita = idcita;
        this.citaiddoc = citaiddoc;
        this.espedoc = espedoc;
        this.fechacita = fechacita;
        this.horacita = horacita;
        this.estadocita = estadocita;
    }


    // Getters y Setters

    public Integer getIdcita() {
        return idcita;
    }

    public void setIdcita(Integer idcita) {
        this.idcita = idcita;
    }

    public String getCitadnipac() {
        return citadnipac;
    }

    public void setCitadnipac(String citadnipac) {
        this.citadnipac = citadnipac;
    }

    public Integer getCitaiddoc() {
        return citaiddoc;
    }

    public void setCitaiddoc(Integer citaiddoc) {
        this.citaiddoc = citaiddoc;
    }

    public LocalDate getFechacita() {
        return fechacita;
    }

    public void setFechacita(LocalDate fechacita) {
        this.fechacita = fechacita;
    }

    public String getHoracita() {
        return horacita;
    }

    public void setHoracita(String horacita) {
        this.horacita = horacita;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getEstadocita() {
        return estadocita;
    }

    public void setEstadocita(String estadocita) {
        this.estadocita = estadocita;
    }


    public String getEspedoc() {
        return espedoc;
    }

    public void setEspedoc(String espedoc) {
        this.espedoc = espedoc;
    }


    @Override
    public String toString() {
        return "Cita [Id=" + getIdcita() + ", Dnipac=" + getCitadnipac() + ", Iddoc="
                + getCitaiddoc() + ", Fecha=" + getFechacita() + ", Hora=" + getHoracita()
                + ", Motivo=" + getMotivo() + ", Diagnostico=" + getDiagnostico() + ", Tratamiento="
                + getTratamiento() + ", Estadocita=" + getEstadocita() + "]";
    }  

}
