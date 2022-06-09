package model;

import java.util.ArrayList;

public class vacuna {
    private String bovino;
    private String fecha;  //cambiar por date
    private String enfermedadATratar;
    private String notas;
    private String idBovino;

    public vacuna(String bovino, String enfermedad, String fecha, String notas, String id) {
        this.bovino = bovino;
        this.enfermedadATratar = enfermedad;
        this.fecha = fecha;
        this.notas = notas;
        this.idBovino = id;
    }

    public void guardarVacuna(String bovino, String fecha, String enfermedadATratar, String notas) {

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


    public String getEnfermedadATratar() {
        return enfermedadATratar;

    }

    public void setNotas(String precio) {
        this.notas = precio;
    }

    public void setEnfermedadATratar(String precio) {
        this.enfermedadATratar = precio;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setBovino(String bovino) {
        this.bovino = bovino;
    }

    public String getIdBovino() {
        return idBovino;
    }

    public void setIdBovino(String idBovino) {
        this.idBovino = idBovino;
    }
}



