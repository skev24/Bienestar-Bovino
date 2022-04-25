package model;

import java.util.ArrayList;

public class venta {
    private String bovino;
    private String monto;
    private String fecha;  //cambiar por date

    public venta(String bovino, String monto, String fecha) {
        this.bovino = bovino;
        this.monto = monto;
        this.fecha = fecha;
    }

    public String getbovino() {
        return bovino;
    }

    public String getmonto() {
        return monto;
    }

    public String getfecha() {
        return fecha;
    }


    public void setPrecio(String monto) {
        this.monto = monto;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setBovino(String bovino) {
        this.fecha = fecha;
    }

}