package model;

public class produccionPeso {
    private String bovino;
    private String dieta;
    private String fecha;  //cambiar por date
    private String peso;
    private String notas;
    private String idBovino;

    public produccionPeso(String bovino, String peso, String fecha, String dieta, String notas, String id) {
        this.bovino = bovino;
        this.peso = peso;
        this.dieta = dieta;
        this.fecha = fecha;
        this.notas = notas;
        this.idBovino = id;
    }

    //Getters
    public String getBovino() {
        return bovino;
    }

    public String getPeso() {
        return peso;
    }

    public String getFecha() {
        return fecha;
    }

    public String getDieta() {
        return dieta;
    }

    public String getNotas() {
        return notas;
    }

    public String getIdBovino() {
        return idBovino;
    }

    //Setters
    public void setBovino(String bovino) {
        this.bovino = bovino;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public void setDieta(String dieta) {
        this.dieta = dieta;
    }

    public void setIdBovino(String idBovino) {
        this.idBovino = idBovino;
    }
}
