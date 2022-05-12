package model;

public class parto {
    private String idVaca, idCria, fecha;
    private Boolean sexo;

    public parto(String idVaca, String idCria, String fecha, Boolean sexo) {
        this.idVaca = idVaca;
        this.idCria = idCria;
        this.fecha = fecha;
        this.sexo = sexo;
    }

    public String getIdVaca() {
        return idVaca;
    }

    public void setIdVaca(String idVaca) {
        this.idVaca = idVaca;
    }

    public String getIdCria() {
        return idCria;
    }

    public void setIdCria(String idCria) {
        this.idCria = idCria;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Boolean getSexo() {
        return sexo;
    }

    public void setSexo(Boolean sexo) {
        this.sexo = sexo;
    }
}
