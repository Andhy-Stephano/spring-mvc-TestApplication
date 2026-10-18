package test;

import util.MapFile;

public class EmployeAvecCIN {

    private String nom;
    private int age;
    private MapFile rectoCIN;

    public EmployeAvecCIN() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public MapFile getRectoCIN() {
        return rectoCIN;
    }

    public void setRectoCIN(MapFile rectoCIN) {
        this.rectoCIN = rectoCIN;
    }
}
