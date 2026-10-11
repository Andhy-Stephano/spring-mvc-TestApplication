package test;

public class Employe {
    private String nom;
    private String poste;
    private int age;

    public Employe(String nom, String poste, int age) {
        this.nom = nom;
        this.poste = poste;
        this.age = age;
    }

    public String getNom() { return nom; }
    public String getPoste() { return poste; }
    public int getAge() { return age; }
}
