package test;

public class Employe {
    private String nom;
    private String poste;
    private int age;

    // Constructeur sans argument : obligatoire pour le data binding objet (Sprint 7 bis)
    public Employe() {
    }

    public Employe(String nom, String poste, int age) {
        this.nom = nom;
        this.poste = poste;
        this.age = age;
    }

    public String getNom() { return nom; }
    public String getPoste() { return poste; }
    public int getAge() { return age; }

    // Setters : utilises par le framework lors du binding objet
    public void setNom(String nom) { this.nom = nom; }
    public void setPoste(String poste) { this.poste = poste; }
    public void setAge(int age) { this.age = age; }
}
