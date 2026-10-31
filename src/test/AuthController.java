package test;

import annotation.Authentified;
import annotation.Controller;
import annotation.GetMapping;
import annotation.RestAPI;
import jakarta.servlet.http.HttpServletRequest;
import model.User;
import util.MySession;

@Controller
public class AuthController {

    // Accès public
    @GetMapping("/auth/public")
    public String actionPublique() {
        return "[Sprint 10 bis] Action publique executee : aucune authentification demandee.";
    }

    // Connexion simple (mode Sprint 10 classique)
    @GetMapping("/auth/login")
    public String login(MySession session) {
        session.set("utilisateur", "Andhy");
        return "[Sprint 10] Connexion reussie pour Andhy. Variable 'utilisateur' mise en session.";
    }

    // Connexion avec PROFIL + ROLE PROFESSEUR
    @GetMapping("/auth/login-prof")
    public String loginProf(MySession session) {
        User user = new User("Rakoto Jean", "rj_prof", "PROFESSEUR");
        session.set("user", user);
        session.set("utilisateur", user.getNom());
        return "[Sprint 10 bis] Connexion reussie en tant que PROFESSEUR<br/>"
             + "Profil mis en session sous la variable 'user' avec role(s) : PROFESSEUR";
    }

    // Connexion avec PROFIL + ROLE ETUDIANT
    @GetMapping("/auth/login-etud")
    public String loginEtud(MySession session) {
        User user = new User("Rasoa Fara", "rf_etud", "ETUDIANT");
        session.set("user", user);
        session.set("utilisateur", user.getNom());
        return "[Sprint 10 bis] Connexion reussie en tant que ETUDIANT<br/>"
             + "Profil mis en session sous la variable 'user' avec role(s) : ETUDIANT";
    }

    // Connexion avec PROFIL + PLUSIEURS ROLES
    @GetMapping("/auth/login-chef")
    public String loginChef(MySession session) {
        User user = new User("Andriamanga Paul", "ap_chef", "PROFESSEUR", "CHEF_DE_DEPARTEMENT");
        session.set("user", user);
        session.set("utilisateur", user.getNom());
        return "[Sprint 10 bis] Connexion reussie avec plusieurs rôles<br/>"
             + "Profil mis en session sous la variable 'user' avec role(s) : PROFESSEUR, CHEF_DE_DEPARTEMENT";
    }

    // Seul PROFESSEUR peut ajouter un cours
    @Authentified(roles = {"PROFESSEUR"})
    @GetMapping("/cours/ajouter")
    public String ajouterCours(HttpServletRequest request) {
        Object profil = request.getAttribute("user");
        if (profil == null) {
            profil = request.getAttribute("utilisateur");
        }
        return "[Sprint 10 bis] Cours ajouté avec succès !<br/>"
             + "Accès réservé uniquement aux PROFESSEUR.<br/>"
             + "Profil dans request : " + profil;
    }

    // PROFESSEUR OU CHEF_DE_DEPARTEMENT peuvent valider
    @Authentified(roles = {"PROFESSEUR", "CHEF_DE_DEPARTEMENT"})
    @GetMapping("/cours/valider")
    public String validerCours(HttpServletRequest request) {
        Object profil = request.getAttribute("user");
        if (profil == null) {
            profil = request.getAttribute("utilisateur");
        }
        return "[Sprint 10 bis] Cours validé avec succès !<br/>"
             + "Accès réservé aux PROFESSEUR ou CHEF_DE_DEPARTEMENT.<br/>"
             + "Profil dans request : " + profil;
    }

    // Mode Sprint 10 classique avec l'init-param
    @Authentified
    @GetMapping("/auth/config")
    public String actionConfiguration() {
        return "[Sprint 10] Accès autorisé via l'init-param 'sessionVariableName' = 'utilisateur'";
    }

    // Mode REST protégé par rôles
    @Authentified(roles = {"PROFESSEUR"})
    @RestAPI
    @GetMapping("/api/cours/ajouter")
    public String ajouterCoursJson() {
        return "{\"message\": \"Cours ajouté via API REST réservée aux PROFESSEUR\"}";
    }

    // Déconnexion
    @GetMapping("/auth/logout")
    public String logout(MySession session) {
        session.clear();
        return "[Sprint 10 bis] Session vidée. Tous les accès protégés sont désormais refusés.";
    }
}
