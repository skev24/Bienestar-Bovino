package model;

public class bovino {
    private String name, id;
    private String raza;
    private String padre, madre;
    private String pesoNacimiento, pesoDestete, peso12Meses;
    private String fecha;
    private String fincaId;
    private Boolean estadoReproductivo, estadoGestacion, sexo, activoEnFinca;


    public bovino(String name, String id, String raza, String padre, String madre, String fecha,
                  String pesoNacimiento, String pesoDestete, String peso12Meses, String fincaId) {
        this.name = name;
        this.id = id;
        this.raza = raza;
        this.padre = padre;
        this.madre = madre;
        this.fecha = fecha;
        this.pesoNacimiento = pesoNacimiento;
        this.pesoDestete = pesoDestete;
        this.peso12Meses = peso12Meses;
        this.fincaId = fincaId;
        this.estadoGestacion = false;
        this.estadoReproductivo = false;
        this.sexo = false;
        this.activoEnFinca = true;
    }

    //Sobre carga de constructor para registrar parto
    public bovino(String name, String id, String raza, String padre, String madre, String fecha, String pesoNacimiento, String fincaId, Boolean sexo){
        this.name = name;
        this.id = id;
        this.raza = raza;
        this.padre = padre;
        this.madre = madre;
        this.fecha = fecha;
        this.pesoNacimiento = pesoNacimiento;
        this.pesoDestete = "0";
        this.peso12Meses = "0";
        this.fincaId = fincaId;
        this.estadoGestacion = false;
        this.estadoReproductivo = false;
        this.sexo = sexo;
        this.activoEnFinca = true;
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

    public String getPesoNacimiento() {
        return pesoNacimiento;
    }

    public void setPesoNacimiento(String pesoNacimiento) {
        this.pesoNacimiento = pesoNacimiento;
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

    public Boolean getEstadoReproductivo() { return estadoReproductivo; }

    public void setEstadoReproductivo(Boolean estadoReproductivo) { this.estadoReproductivo = estadoReproductivo; }

    public Boolean getEstadoGestacion() { return estadoGestacion; }

    public void setEstadoGestacion(Boolean estadoGestacion) { this.estadoGestacion = estadoGestacion; }

    public Boolean getSexo() { return sexo; }

    public void setSexo(Boolean sexo) { this.sexo = sexo; }
}
