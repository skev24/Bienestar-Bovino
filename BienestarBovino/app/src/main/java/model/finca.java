package model;

public class finca {
    private String name;
    private String tam;
    private String user;

    public finca(){

    }

    public finca(String name, String tam, String user) {
        this.name = name;
        this.tam = tam;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTam() {
        return tam;
    }

    public void setTam(String tam) {
        this.tam = tam;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
