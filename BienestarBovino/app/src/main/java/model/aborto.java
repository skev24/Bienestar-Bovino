package model;

public class aborto {
    private String idVaca, fecha;

    public aborto(String idVaca, String fecha) {
        this.idVaca = idVaca;
        this.fecha = fecha;
    }

    public String getIdVaca() {
        return idVaca;
    }

    public void setIdVaca(String idVaca) {
        this.idVaca = idVaca;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
