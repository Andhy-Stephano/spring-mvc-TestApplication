package test;

import annotation.Controller;
import annotation.GetMapping;

@Controller
public class EtudiantController {

    // Compteur d'appels : appartient a l'INSTANCE du controleur
    private int nbAppels = 0;

    // Le constructeur affiche un message dans la console Tomcat.
    // On verra ainsi combien de fois une instance est creee.
    public EtudiantController() {
        System.out.println("[EtudiantController] Constructeur appele -> nouvelle instance (id "
            + System.identityHashCode(this) + ")");
    }

    @GetMapping("/etudiant/methode1")
    public String methode1() {
        nbAppels++;
        return "methode1 | instance id = " + System.identityHashCode(this)
             + " | nbAppels = " + nbAppels;
    }

    @GetMapping("/etudiant/methode2")
    public String methode2() {
        nbAppels++;
        return "methode2 | instance id = " + System.identityHashCode(this)
             + " | nbAppels = " + nbAppels;
    }
}
