package test;

import annotation.Controller;
import annotation.GetMapping;
import annotation.HttpMethod;
import jakarta.servlet.http.HttpServletRequest;
import util.MapFile;

@Controller
public class EmployeController {

    // Vue du formulaire Sprint 8 : GET /employe/formulaire-cin
    @GetMapping(value = "/employe/formulaire-cin", method = {HttpMethod.GET})
    public String formulaireCIN() {
        return "<!DOCTYPE html>"
            + "<html>"
            + "<head><title>Sprint 8 - Formulaire CIN</title></head>"
            + "<body>"
            + "<h2>[Sprint 8] Ajout d'un employe avec photo de CIN (upload)</h2>"
            + "<p>Le formulaire utilise <b>multipart/form-data</b> et un champ "
            + "<b>&lt;input type=\"file\"&gt;</b> pour envoyer le fichier.</p>"
            + "<form action=\"http://localhost:8080/TestApplication/employe/save-cin\" "
            + "method=\"POST\" enctype=\"multipart/form-data\">"
            + "Nom : <input type=\"text\" name=\"nom\"><br>"
            + "Age : <input type=\"text\" name=\"age\"><br>"
            + "Recto CIN : <input type=\"file\" name=\"rectoCIN\"><br>"
            + "<button type=\"submit\">Soumettre</button>"
            + "</form>"
            + "</body>"
            + "</html>";
    }

    // Cas principal Sprint 8 : POST /employe/save-cin (objet EmployeAvecCIN + MapFile)
    @GetMapping(value = "/employe/save-cin", method = {HttpMethod.POST})
    public String sauvegarderAvecCIN(EmployeAvecCIN employe, HttpServletRequest request) {

        MapFile fichier = employe.getRectoCIN();
        String chemin = (String) request.getAttribute("cheminFichier");

        if (fichier == null) {
            return "[Sprint 8] Aucun fichier recu pour le champ rectoCIN.";
        }

        return "[Sprint 8] Employe recu :"
            + " nom=" + employe.getNom()
            + ", age=" + employe.getAge()
            + "<br>Fichier MapFile :"
            + " fileName=" + fichier.getFileName()
            + ", contentType=" + fichier.getContentType()
            + ", taille=" + fichier.getSize() + " octets"
            + "<br>Fichier enregistre sur le serveur dans : " + chemin;
    }
}