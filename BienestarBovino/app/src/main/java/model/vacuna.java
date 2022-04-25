package model;

import java.util.ArrayList;

public class vacuna {
    private ArrayList<String> id;
    private String bovino;
    private String nombre;
    private String fecha;  //cambiar por date
    private String enfermedadATratar;
    private String notas;

    public vacuna(String enfermedad, String fecha, String bovino, String notas) {
        this.bovino = bovino;
        this.enfermedadATratar = enfermedad;
        this.fecha = fecha;
        this.notas = notas;
    }

    public void guardarVacuna(String fecha, String enfermedadATratar, String notas) {

    }

    public ArrayList<String> getId() {
        return id;
    }


    public String getIdentificacionBovino() {
        return bovino;
    }


    public String getNombre() {
        return nombre;
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



