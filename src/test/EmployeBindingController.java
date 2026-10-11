package test;

import annotation.Controller;
import annotation.GetMapping;
import annotation.HttpMethod;
import annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class EmployeBindingController {

    // Vue du formulaire : GET /employe/formulaire
    @GetMapping(value = "/employe/formulaire", method = {HttpMethod.GET})
    public String formulaire() {
        return "<!DOCTYPE html>"
            + "<html>"
            + "<head><title>Sprint 7 - Formulaire Employe</title></head>"
            + "<body>"
            + "<h2>[Sprint 7] Ajout d'un employe (data binding)</h2>"
            + "<form action=\"http://localhost:8080/TestApplication/employe/save\" method=\"POST\">"
            + "Nom : <input type=\"text\" name=\"nom\" required><br>"
            + "Age : <input type=\"text\" name=\"age\" required><br>"
            + "Salaire : <input type=\"text\" name=\"salaire\" required><br>"
            + "<button type=\"submit\">Soumettre</button>"
            + "</form>"
            + "<hr>"
            + "<h3>Autres tests du Sprint 7</h3>"
            + "<form action=\"http://localhost:8080/TestApplication/employe/save-name\" method=\"POST\">"
            + "<p>Binding par nom de parametre (sans @RequestParam)</p>"
            + "Nom : <input type=\"text\" name=\"nom\"><br>"
            + "Age : <input type=\"text\" name=\"age\"><br>"
            + "<button type=\"submit\">Tester binding par nom</button>"
            + "</form>"
            + "<form action=\"http://localhost:8080/TestApplication/employe/check\" method=\"POST\">"
            + "<p>Parametre age obligatoire ABSENT -> BindingException</p>"
            + "<button type=\"submit\">Tester age obligatoire manquant</button>"
            + "</form>"
            + "<form action=\"http://localhost:8080/TestApplication/employe/save-object\" method=\"POST\">"
            + "<p>Sprint 7 bis - Binding d'un OBJET Employe (setters)</p>"
            + "Nom : <input type=\"text\" name=\"nom\"><br>"
            + "Poste : <input type=\"text\" name=\"poste\"><br>"
            + "Age : <input type=\"text\" name=\"age\"><br>"
            + "<button type=\"submit\">Tester objet Employe</button>"
            + "</form>"
            + "<p><a href=\"http://localhost:8080/TestApplication/employe/formulaire-objet\">"
            + "Voir le formulaire objet separe</a></p>"
            + "<form action=\"http://localhost:8080/TestApplication/employe/request\" method=\"POST\">"
            + "<p>Injection de HttpServletRequest</p>"
            + "<button type=\"submit\">Tester injection</button>"
            + "</form>"
            + "</body>"
            + "</html>";
    }

    // Cas principal : POST /employe/save avec String, int, double (@RequestParam)
    @GetMapping(value = "/employe/save", method = {HttpMethod.POST})
    public String save(
            @RequestParam("nom") String nom,
            @RequestParam("age") int age,
            @RequestParam("salaire") double salaire) {
        return "Employe enregistre : nom=" + nom + ", age=" + age + ", salaire=" + salaire;
    }

    // Cas 2 : binding par NOM du parametre (sans @RequestParam) -> flag -parameters
    @GetMapping(value = "/employe/save-name", method = {HttpMethod.POST})
    public String saveParNom(String nom, int age) {
        return "Binding par nom : nom=" + nom + ", age=" + age;
    }

    // Cas 3 : parametre obligatoire absent -> BindingException
    @GetMapping(value = "/employe/check", method = {HttpMethod.POST})
    public String check(@RequestParam(value = "age", required = true) int age) {
        return "Age obligatoire fourni : " + age;
    }

    // Cas 4 : SPRINT 7 BIS - parametre OBJET Employe (rempli par le framework)
    @GetMapping(value = "/employe/save-object", method = {HttpMethod.POST})
    public String saveEmploye(Employe employe) {
        return "Objet Employe recu : nom=" + employe.getNom()
                + ", poste=" + employe.getPoste()
                + ", age=" + employe.getAge();
    }

    // Vue du formulaire objet : GET /employe/formulaire-objet
    @GetMapping(value = "/employe/formulaire-objet", method = {HttpMethod.GET})
    public String formulaireObjet() {
        return "<!DOCTYPE html>"
            + "<html>"
            + "<head><title>Sprint 7 bis - Formulaire Objet</title></head>"
            + "<body>"
            + "<h2>[Sprint 7 bis] Binding d'un objet Employe</h2>"
            + "<p>Le framework instancie Employe et remplit nom / poste / age "
            + "a partir de la requete (setter setXxx, sinon acces direct).</p>"
            + "<form action=\"http://localhost:8080/TestApplication/employe/save-objet\" method=\"POST\">"
            + "Nom : <input type=\"text\" name=\"nom\"><br>"
            + "Poste : <input type=\"text\" name=\"poste\"><br>"
            + "Age : <input type=\"text\" name=\"age\"><br>"
            + "<button type=\"submit\">Soumettre</button>"
            + "</form>"
            + "</body>"
            + "</html>";
    }

    // Cas principal 7 bis : POST /employe/save-objet avec argument Employe
    @GetMapping(value = "/employe/save-objet", method = {HttpMethod.POST})
    public String saveObjet(Employe employe) {
        return "Employe complet : nom=" + employe.getNom()
                + ", poste=" + employe.getPoste()
                + ", age=" + employe.getAge();
    }

    // Cas 5 : injection de HttpServletRequest
    @GetMapping(value = "/employe/request", method = {HttpMethod.POST})
    public String showRequest(HttpServletRequest request) {
        return "URL injectee : " + request.getRequestURI() + " | methode : " + request.getMethod();
    }
}