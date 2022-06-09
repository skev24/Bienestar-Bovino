package model;

public class personal {
    private String nombre;
    private String id;
    private String apellido;
    private String tipo;
    private String idFinca;

    public personal(String nombre, String id, String apellido, String tipo, String idFinca) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.id = id;
        this.tipo = tipo;
        this.idFinca = idFinca;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIdFinca(String idFinca){
        this.idFinca = idFinca;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido){
        this.apellido = apellido;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdFinca(){return idFinca;}


    public String getApellido() {
        return apellido;
    }

    public String getId() {
        return id;
    }

    public  String getTipo(){
        return tipo;
    }
}
