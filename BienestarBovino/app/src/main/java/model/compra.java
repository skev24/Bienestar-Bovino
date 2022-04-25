package model;

import java.util.ArrayList;

public class compra {
    //Datos de bovino


    //Datos de compra
    private int precio;
    private String fecha;  //cambiar por date
    private String bovinoId;

    public compra( int precio, String fecha, String bovinoId) {

        this.precio = precio;
        this.fecha = fecha;
        this.bovinoId = bovinoId;

    }


    public int getPrecio() {
        return precio;
    }

    public String getFecha() {
        return fecha;
    }

    public String getBovinoId() {
        return bovinoId;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setBovinoId(String bovinoId) {
        this.bovinoId = bovinoId;
    }
}

