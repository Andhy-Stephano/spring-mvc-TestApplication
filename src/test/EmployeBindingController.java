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
            + "<p>Parametre objet Employe -> BindingException (Sprint 7 bis)</p>"
            + "<button type=\"submit\">Tester objet complexe</button>"
            + "</form>"
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

    // Cas 4 : parametre objet complexe -> BindingException (reserve au Sprint 7 bis)
    @GetMapping(value = "/employe/save-object", method = {HttpMethod.POST})
    public String saveEmploye(Employe employe) {
        return "Objet Employe recu : " + employe.getNom();
    }

    // Cas 5 : injection de HttpServletRequest
    @GetMapping(value = "/employe/request", method = {HttpMethod.POST})
    public String showRequest(HttpServletRequest request) {
        return "URL injectee : " + request.getRequestURI() + " | methode : " + request.getMethod();
    }
}