package test;

import java.util.List;

/**
 * Modele utilise par les tests du Sprint 7 bis.
 * - pas de setters : le framework doit injecter par ACCES DIRECT (reflexion)
 * - champ 'competences' de type List : non simple -> ignore (reste null)
 */
public class Personne {
    private String nom;
    private int age;
    private List<String> competences;

    public Personne() {
    }

    public String getNom() { return nom; }
    public int getAge() { return age; }
    public List<String> getCompetences() { return competences; }
}