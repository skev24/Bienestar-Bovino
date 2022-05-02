package model;

public class estadoReproductivo {
    private String idBovino, nacimiento, destete, novilla, adulta;

    public estadoReproductivo(String idBovino, String nacimiento, String destete, String novilla, String adulta) {
        this.idBovino = idBovino;
        this.destete = destete;
        this.novilla = novilla;
        this.nacimiento = nacimiento;
        this.adulta = adulta;
    }

    public String getIdBovino() {
        return idBovino;
    }

    public void setIdBovino(String idBovino) {
        this.idBovino = idBovino;
    }

    public String getDestete() {
        return destete;
    }

    public void setDestete(String destete) {
        this.destete = destete;
    }

    public String getNovilla() {
        return novilla;
    }

    public void setNovilla(String novilla) {
        this.novilla = novilla;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getAdulta() {
        return adulta;
    }

    public void setAdulta(String adulta) {
        this.adulta = adulta;
    }
}
