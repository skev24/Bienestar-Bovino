package model;

import java.util.ArrayList;

public class vacuna {
    private ArrayList<String> id;
    private String identificacionBovino;
    private String nombre;
    private String raza;
    private String fecha;  //cambiar por date
    private String enfermedadATratar;
    private String notas;

    public vacuna() {

    }

    public void guardarVacuna(String fecha, String enfermedadATratar, String notas) {

    }

    public ArrayList<String> getId() {
        return id;
    }


    public String getIdentificacionBovino() {
        return identificacionBovino;
    }


    public String getNombre() {
        return nombre;
    }


    public String getRaza() {
        return raza;
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
}



