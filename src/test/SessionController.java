package test;

import annotation.Controller;
import annotation.GetMapping;
import util.MySession;

@Controller
public class SessionController {

    // Attribut du controleur = partage par TOUS les utilisateurs
    // (ne monte vraiment qu'avec l'etape 7)
    private int visitesGlobales = 0;

    @GetMapping("/session/login")
    public String login(MySession session) {
        session.set("utilisateur", "Andhy");
        session.set("role", "etudiant");
        return "Utilisateur enregistre dans la session";
    }

    @GetMapping("/session/profil")
    public String profil(MySession session) {
        String utilisateur = session.get("utilisateur", String.class);
        if (utilisateur == null) {
            return "Aucun utilisateur dans la session. Va d'abord sur /session/login";
        }
        return "Bonjour " + utilisateur + " (role : " + session.get("role", String.class) + ")";
    }

    @GetMapping("/session/compteur")
    public String compteur(MySession session) {
        Integer nb = session.get("compteur", Integer.class);
        nb = (nb == null) ? 1 : nb + 1;
        session.set("compteur", nb);
        visitesGlobales++;
        return "Tes visites (session) : " + nb
             + " | Visites de tout le monde (controleur) : " + visitesGlobales;
    }

    @GetMapping("/session/infos")
    public String infos(MySession session) {
        return "Id : " + session.getId()
             + " | Creee le : " + new java.util.Date(session.getCreationTime())
             + " | Dernier acces : " + new java.util.Date(session.getLastAccessTime());
    }

    @GetMapping("/session/logout")
    public String logout(MySession session) {
        session.clear();
        return "Session videe";
    }
}