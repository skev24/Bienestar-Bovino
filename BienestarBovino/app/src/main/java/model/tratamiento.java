package model;

import java.util.ArrayList;

public class tratamiento {
    private String bovino;
    private String diagnostico;
    private String fecha;  //cambiar por date
    private String diasTratamiento;
    private String notas;
    private String idBovino;

    public tratamiento(String bovino, String diagnostico, String diasTratamiento, String fecha, String notas, String id) {
        this.bovino = bovino;
        this.diagnostico = diagnostico;
        this.fecha = fecha;
        this.diasTratamiento = diasTratamiento;
        this.notas = notas;
        this.idBovino = id;
    }

    public String getBovino() {
        return bovino;
    }

    public String getNotas() {
        return notas;
    }

    public String getfecha() {
        return fecha;
    }

    public String getDiasTratamiento() {
        return diasTratamiento;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setNotas(String precio) {
        this.notas = precio;
    }

    public void setDiagnostico(String precio) {
        this.diagnostico = precio;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setBovino(String bovino) {
        this.bovino = bovino;
    }

    public void setDiasTratamiento(String diasTratamiento) {
        this.diasTratamiento = diasTratamiento;

    }

    public String getIdBovino() {
        return idBovino;
    }

    public void setIdBovino(String idBovino) {
        this.idBovino = idBovino;
    }
}



