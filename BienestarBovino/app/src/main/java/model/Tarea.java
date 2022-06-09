package model;

public class Tarea {
    private String encargadoTarea;
    private String descripcion;
    private String fecha; //cambiar por date
    private boolean concluida;
    private String idFinca;

    public Tarea(String fecha, String encargadoTarea, String descripcion, boolean concluida, String idFinca) {
        this.encargadoTarea = encargadoTarea;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.concluida = concluida;
        this.idFinca = idFinca;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setIdFinca(String idFinca){
        this.idFinca = idFinca;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setConcluida(boolean concluida){
        this.concluida = concluida;
    }

    public void setEncargadoTarea(String encargadoTarea) {
        this.encargadoTarea = encargadoTarea;
    }

    public String getfecha() {
        return fecha;
    }

    public String getIdFinca(){return idFinca;}


    public String getEncargadoTarea() {
        return encargadoTarea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public  boolean getConcluida(){
        return concluida;
    }
}
