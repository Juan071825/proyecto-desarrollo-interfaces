package com.clase.modelo;

public class Doctor {
    private String iddoc;
    private String apeldoc;
    private String nomdoc;
    private String movildoc;
    private String maildoc;
    private String espedoc;

    public Doctor(String iddoc, String apeldoc, String nomdoc,
            String movildoc, String mailpac,
            String espedoc) {
        this.iddoc = iddoc;
        this.apeldoc = apeldoc;
        this.nomdoc = nomdoc;
        this.movildoc = movildoc;
        this.maildoc = maildoc;
        this.espedoc = espedoc;
    }

    //Constructor para el paciente de la tabla
    public Doctor(String iddoc, String apeldoc, String nomdoc,
            String movildoc, String espedoc) {
        this.iddoc = iddoc;
        this.apeldoc = apeldoc;
        this.nomdoc = nomdoc;
        this.movildoc = movildoc;
        this.espedoc = espedoc;
    }

    public String getIddoc() {
        return iddoc;
    }

    public void setIddoc(String iddoc) {
        this.iddoc = iddoc;
    }
    public String getNombre() {
        return nomdoc;
    }

    public void setNombre(String nomedoc) {
        this.nomdoc = nomedoc;
    }

    public String getApellidos() {
        return apeldoc;
    }

    public void setApellidos(String apeldoc) {
        this.apeldoc = apeldoc;
    }

    public String getMovil() {
        return movildoc;
    }

    public void setMovil(String movildoc) {
        this.movildoc = movildoc;
    }

    public String getMail() {
        return maildoc;
    }

    public void setMail(String maildoc) {
        this.maildoc = maildoc;
    }

    public String getEspecialidad() {
        return espedoc;
    }

    public void setEspecialidad(String espedoc) {
        this.espedoc = espedoc;
    }
}