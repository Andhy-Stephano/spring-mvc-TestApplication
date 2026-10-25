package test;

import annotation.Authentified;
import annotation.Controller;
import annotation.GetMapping;
import annotation.RestAPI;
import jakarta.servlet.http.HttpServletRequest;
import util.MySession;

@Controller
public class AuthController {

    // URL publique : accessible sans etre connecte
    @GetMapping("/auth/public")
    public String actionPublique() {
        return "[Sprint 10] Action publique executee : aucune authentification demandee.";
    }

    // URL de connexion : la personne est authentifiee et on met la variable en session
    @GetMapping("/auth/login")
    public String login(MySession session) {
        session.set("utilisateur", "Andhy");
        session.set("role", "etudiant");
        return "[Sprint 10] Connexion reussie pour Andhy."
             + " La variable de session 'utilisateur' est maintenant presente."
             + " Va sur /auth/action";
    }

    // URL protegee : le framework refuse l'acces si 'utilisateur' est absent de la session
    @Authentified
    @GetMapping("/auth/action")
    public String actionProtegee(MySession session, HttpServletRequest request) {
        String utilisateur = session.get("utilisateur", String.class);
        Object attributRequete = request.getAttribute("utilisateur");
        return "[Sprint 10] Action protegee executee pour " + utilisateur
             + " (attribut de requete = " + attributRequete + ")";
    }

    // Meme controle mais en mode REST : renvoie du JSON ou une erreur 401 JSON
    @Authentified
    @RestAPI
    @GetMapping("/auth/api/action")
    public String actionProtegeeJson() {
        return "{\"message\": \"Action REST protegee executee\"}";
    }

    // URL de deconnexion : on vide la session
    @GetMapping("/auth/logout")
    public String logout(MySession session) {
        session.clear();
        return "[Sprint 10] Session videe : /auth/action refusera desormais l'acces.";
    }
}
