package model;

public class estadoGestacion {

    private String idBovinoVaca, idBovinoToro, tipo, fecha;
    private Boolean estadoFinalizado;

    public estadoGestacion(String idBovinoVaca, String idBovinoToro, String tipo, String fecha) {
        this.idBovinoVaca = idBovinoVaca;
        this.idBovinoToro = idBovinoToro;
        this.tipo = tipo;
        this.fecha = fecha;
        this.estadoFinalizado = false;
    }

    public String getIdBovinoVaca() {
        return idBovinoVaca;
    }

    public void setIdBovinoVaca(String idBovinoVaca) {
        this.idBovinoVaca = idBovinoVaca;
    }

    public String getIdBovinoToro() {
        return idBovinoToro;
    }

    public void setIdBovinoToro(String idBovinoToro) {
        this.idBovinoToro = idBovinoToro;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Boolean getEstadoFinalizado() {
        return estadoFinalizado;
    }

    public void setEstadoFinalizado(Boolean estadoFinalizado) {
        this.estadoFinalizado = estadoFinalizado;
    }
}
