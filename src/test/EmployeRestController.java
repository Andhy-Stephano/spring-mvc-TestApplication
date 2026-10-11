package test;

import annotation.Controller;
import annotation.GetMapping;
import annotation.RestAPI;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeRestController {

    // Cas 1 : retourne un objet Java -> Gson convertit en JSON
    @GetMapping("/api/employe")
    @RestAPI
    public Object getEmploye() {
        return new Employe("Rakoto", "Developpeur", 20);
    }

    @GetMapping("/api/bonjour")
    @RestAPI
public String bonjour() {
    return "Bonjour";
}
    // Cas 2 : retourne une liste -> Gson convertit en tableau JSON
    @GetMapping("/api/employes")
    @RestAPI
    public Object getEmployes() {
        List<Employe> liste = new ArrayList<>();
        liste.add(new Employe("Rakoto", "Developpeur", 20));
        liste.add(new Employe("Bema", "Designer", 21));
        return liste;
    }

    // Cas 3 : retourne un String simple -> affiche tel quel
    @GetMapping("/api/message")
    @RestAPI
    public String getMessage() {
        return "Bonjour depuis l API REST";
    }

    // Cas 4 : retourne un JSON deja construit manuellement
    @GetMapping("/api/statut")
    @RestAPI(alreadyJson = true)
    public String getStatut() {
        return "{\"statut\": \"OK\", \"version\": \"1.0\"}";
    }
}