package com.clase.modelo;

public class Doctor {
    private Integer iddoc;
    private String apeldoc;
    private String nomdoc;
    private Boolean coledoc;
    private String movildoc;
    private String emaildoc;
    private String espedoc;

    public Doctor(Integer iddoc, String apeldoc, String nomdoc, Boolean coledoc,
            String movildoc, String emailpac,
            String espedoc) {
        this.iddoc = iddoc;
        this.nomdoc = nomdoc;
        this.apeldoc = apeldoc;
        this.movildoc = movildoc;
        this.coledoc = coledoc;
        this.emaildoc = emaildoc;
        this.espedoc = espedoc;
    }

    public Doctor(String apeldoc, String nomdoc, Boolean coledoc,
            String movildoc, String emailpac,
            String espedoc) {
        this.nomdoc = nomdoc;
        this.apeldoc = apeldoc;
        this.movildoc = movildoc;
        this.coledoc = coledoc;
        this.emaildoc = emaildoc;
        this.espedoc = espedoc;
    }

    //Constructor para el doctor de la tabla
    public Doctor(Integer iddoc, String apeldoc, String nomdoc,
            String movildoc, String espedoc) {
        this.iddoc = iddoc;
        this.apeldoc = apeldoc;
        this.nomdoc = nomdoc;
        this.movildoc = movildoc;        
        this.espedoc = espedoc;
    }

    public Integer getIddoc() {
        return this.iddoc;
    }

    public void setIddoc(Integer iddoc) {
        this.iddoc = iddoc;
    }
    public String getNombre() {
        return this.nomdoc;
    }

    public void setNombre(String nomdoc) {
        this.nomdoc = nomdoc;
    }

    public String getApellidos() {
        return this.apeldoc;
    }

    public void setApellidos(String apeldoc) {
        this.apeldoc = apeldoc;
    }

    public String getMovil() {
        return this.movildoc;
    }

    public void setMovil(String movildoc) {
        this.movildoc = movildoc;
    }

    public String getEmail() {
        return this.emaildoc;
    }

    public void setEmail(String maildoc) {
        this.emaildoc = maildoc;
    }

    public String getEspecialidad() {
        return this.espedoc;
    }

    public void setEspecialidad(String espedoc) {
        this.espedoc = espedoc;
    }

    public Boolean getColegiado() {
        return this.coledoc;
    }

    public void setColegiado(Boolean coledoc) {
        this.coledoc = coledoc;
    }
}