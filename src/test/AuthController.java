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
        return "[Sprint 10 bis] Action publique executee : aucune authentification demandee.";
    }

    // Connexion utilisateur : la variable 'utilisateur' est mise en session
    @GetMapping("/auth/login")
    public String login(MySession session) {
        session.set("utilisateur", "Andhy");
        session.set("role", "etudiant");
        return "[Sprint 10 bis] Connexion reussie pour Andhy."
             + " La variable 'utilisateur' est presente en session."
             + " Va sur /auth/action, /auth/config ou /auth/admin";
    }

    // Connexion administrateur : on utilise une AUTRE variable de session
    @GetMapping("/auth/login-admin")
    public String loginAdmin(MySession session) {
        session.set("admin", "Administrateur");
        return "[Sprint 10 bis] Connexion admin reussie."
             + " La variable 'admin' est presente en session. Va sur /auth/admin";
    }

    // L'annotation impose le nom de la variable de session (convention)
    @Authentified("utilisateur")
    @GetMapping("/auth/action")
    public String actionProtegee(MySession session, HttpServletRequest request) {
        String utilisateur = session.get("utilisateur", String.class);
        Object attributRequete = request.getAttribute("utilisateur");
        return "[Sprint 10 bis] Action protegee par @Authentified(\"utilisateur\")"
             + " executee pour " + utilisateur
             + " (attribut de requete = " + attributRequete + ")";
    }

    // Autre variable de session : seuls les administrateurs passent ici
    @Authentified("admin")
    @GetMapping("/auth/admin")
    public String actionAdmin(MySession session, HttpServletRequest request) {
        String admin = session.get("admin", String.class);
        Object attributRequete = request.getAttribute("admin");
        return "[Sprint 10 bis] Action protegee par @Authentified(\"admin\")"
             + " executee pour " + admin
             + " (attribut de requete = " + attributRequete + ")";
    }

    // Aucune valeur dans l'annotation : le nom vient de l'init-param (configuration)
    @Authentified
    @GetMapping("/auth/config")
    public String actionConfiguration(MySession session, HttpServletRequest request) {
        String utilisateur = session.get("utilisateur", String.class);
        Object attributRequete = request.getAttribute("utilisateur");
        return "[Sprint 10 bis] Action protegee par l'init-param 'sessionVariableName'"
             + " executee pour " + utilisateur
             + " (attribut de requete = " + attributRequete + ")";
    }

    // Meme controle mais en mode REST : renvoie du JSON ou une erreur 401 JSON
    @Authentified("utilisateur")
    @RestAPI
    @GetMapping("/auth/api/action")
    public String actionProtegeeJson() {
        return "{\"message\": \"Action REST protegee executee (Sprint 10 bis)\"}";
    }

    // URL de deconnexion : on vide la session
    @GetMapping("/auth/logout")
    public String logout(MySession session) {
        session.clear();
        return "[Sprint 10 bis] Session videe : les actions protegees refuseront l'acces.";
    }
}
