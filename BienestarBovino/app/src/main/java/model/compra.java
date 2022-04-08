package model;

import java.util.ArrayList;

public class compra {
    private ArrayList<String> id;
    private String identificacionBovino;
    private String nombre;
    private String raza;
    private String precio;
    private String fecha;  //cambiar por date

    public compra() {

    }

    public void guardarCompra(String precio, String fecha) {

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


    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
