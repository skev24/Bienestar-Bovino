package model;

public class bovino {
    private String name, id;
    private String raza;
    private String padre, madre;
    private String pesoNacemiento, pesoDestete, peso12Meses;
    private String fecha;
    private String fincaId;
    private Boolean EstadoReproductivo, EstadoGestacion, sexo;


    public bovino(String name, String id, String raza, String padre, String madre, String fecha,
                  String pesoNacemiento, String pesoDestete, String peso12Meses, String fincaId) {
        this.name = name;
        this.id = id;
        this.raza = raza;
        this.padre = padre;
        this.madre = madre;
        this.fecha = fecha;
        this.pesoNacemiento = pesoNacemiento;
        this.pesoDestete = pesoDestete;
        this.peso12Meses = peso12Meses;
        this.fincaId = fincaId;
        this.EstadoGestacion = false;
        this.EstadoReproductivo = false;
        this.sexo = false;
    }

    public String getFincaId() {
        return fincaId;
    }

    public void setFincaId(String fincaId) {
        this.fincaId = fincaId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public String getMadre() {
        return madre;
    }

    public void setMadre(String madre) {
        this.madre = madre;
    }

    public String getPesoNacemiento() {
        return pesoNacemiento;
    }

    public void setPesoNacemiento(String pesoNacemiento) {
        this.pesoNacemiento = pesoNacemiento;
    }

    public String getPesoDestete() {
        return pesoDestete;
    }

    public void setPesoDestete(String pesoDestete) {
        this.pesoDestete = pesoDestete;
    }

    public String getPeso12Meses() {
        return peso12Meses;
    }

    public void setPeso12Meses(String peso12Meses) {
        this.peso12Meses = peso12Meses;
    }

    public Boolean getEstadoReproductivo() { return EstadoReproductivo; }

    public void setEstadoReproductivo(Boolean estadoReproductivo) { EstadoReproductivo = estadoReproductivo; }

    public Boolean getEstadoGestacion() { return EstadoGestacion; }

    public void setEstadoGestacion(Boolean estadoGestacion) { EstadoGestacion = estadoGestacion; }

    public Boolean getSexo() { return sexo; }

    public void setSexo(Boolean sexo) { this.sexo = sexo; }
}
