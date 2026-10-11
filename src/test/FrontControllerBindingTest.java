package test;

import exception.BindingException;
import servlet.FrontControllerServlet;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class FrontControllerBindingTest {

    private static int ok = 0;
    private static int ko = 0;

    public static void main(String[] args) throws Exception {
        System.out.println("=== TEST SPRINT 7 : DATA BINDING ===");

        // ---------- 1) isSimpleType ----------
        System.out.println("--- 1) isSimpleType ---");
        verifier("isSimpleType(String)", FrontControllerServlet.isSimpleType(String.class), true);
        verifier("isSimpleType(int)", FrontControllerServlet.isSimpleType(int.class), true);
        verifier("isSimpleType(long)", FrontControllerServlet.isSimpleType(long.class), true);
        verifier("isSimpleType(double)", FrontControllerServlet.isSimpleType(double.class), true);
        verifier("isSimpleType(float)", FrontControllerServlet.isSimpleType(float.class), true);
        verifier("isSimpleType(short)", FrontControllerServlet.isSimpleType(short.class), true);
        verifier("isSimpleType(byte)", FrontControllerServlet.isSimpleType(byte.class), true);
        verifier("isSimpleType(boolean)", FrontControllerServlet.isSimpleType(boolean.class), true);
        verifier("isSimpleType(char)", FrontControllerServlet.isSimpleType(char.class), true);
        verifier("isSimpleType(Integer)", FrontControllerServlet.isSimpleType(Integer.class), true);
        verifier("isSimpleType(Double)", FrontControllerServlet.isSimpleType(Double.class), true);
        verifier("isSimpleType(Employe)==false", FrontControllerServlet.isSimpleType(Employe.class), false);
        verifier("isSimpleType(List)==false", FrontControllerServlet.isSimpleType(List.class), false);

        // ---------- 2) defaultValue ----------
        System.out.println("--- 2) defaultValue ---");
        verifier("defaultValue(int)=0", FrontControllerServlet.defaultValue(int.class), 0);
        verifier("defaultValue(long)=0L", FrontControllerServlet.defaultValue(long.class), 0L);
        verifier("defaultValue(double)=0.0", FrontControllerServlet.defaultValue(double.class), 0.0);
        verifier("defaultValue(float)=0.0f", FrontControllerServlet.defaultValue(float.class), 0.0f);
        verifier("defaultValue(boolean)=false", FrontControllerServlet.defaultValue(boolean.class), false);
        verifier("defaultValue(char)=caractere nul", FrontControllerServlet.defaultValue(char.class), '\0');
        verifier("defaultValue(String)=null", FrontControllerServlet.defaultValue(String.class), null);
        verifier("defaultValue(Boolean wrapper)=null", FrontControllerServlet.defaultValue(Boolean.class), null);

        // ---------- 3) convert ----------
        System.out.println("--- 3) convert ---");
        verifier("convert('123', int)=123", FrontControllerServlet.convert("123", int.class), 123);
        verifier("convert('2.5', double)=2.5", FrontControllerServlet.convert("2.5", double.class), 2.5);
        verifier("convert('9', long)=9L", FrontControllerServlet.convert("9", long.class), 9L);
        verifier("convert('1.5', float)=1.5f", FrontControllerServlet.convert("1.5", float.class), 1.5f);
        verifier("convert('2', short)=2", FrontControllerServlet.convert("2", short.class), (short) 2);
        verifier("convert('3', byte)=3", FrontControllerServlet.convert("3", byte.class), (byte) 3);
        verifier("convert('true', boolean)=true", FrontControllerServlet.convert("true", boolean.class), true);
        verifier("convert('a', char)='a'", FrontControllerServlet.convert("a", char.class), 'a');
        verifier("convert('hello', String)=hello", FrontControllerServlet.convert("hello", String.class), "hello");
        verifierThrow("convert('abc', int) -> BindingException", () -> FrontControllerServlet.convert("abc", int.class));
        verifierThrow("convert('ab', char) -> BindingException", () -> FrontControllerServlet.convert("ab", char.class));
        verifierThrow("convert('12.5', int) -> BindingException", () -> FrontControllerServlet.convert("12.5", int.class));

        // ---------- 4) bind ----------
        System.out.println("--- 4) bind ---");

        // Methodes du controleur de test
        Method mSave = EmployeBindingController.class.getMethod(
                "save", String.class, int.class, double.class);
        Parameter pNom = mSave.getParameters()[0];
        Parameter pAge = mSave.getParameters()[1];
        Parameter pSalaire = mSave.getParameters()[2];

        Map<String, String> params = new HashMap<>();
        params.put("nom", "Rakoto");
        params.put("age", "25");
        params.put("salaire", "2500.75");

        verifier("bind(nom)=Rakoto", FrontControllerServlet.bind(params, pNom), "Rakoto");
        verifier("bind(age)=25", FrontControllerServlet.bind(params, pAge), 25);
        verifier("bind(salaire)=2500.75", FrontControllerServlet.bind(params, pSalaire), 2500.75);

        // Parametre absent mais NON obligatoire -> valeur par defaut (age -> 0)
        Map<String, String> paramsSansAge = new HashMap<>();
        paramsSansAge.put("nom", "Bema");
        verifier("bind(age absent, non requis)=0", FrontControllerServlet.bind(paramsSansAge, pAge), 0);
        verifier("bind(nom absent, non requis)=null",
                FrontControllerServlet.bind(new HashMap<>(), pNom), null);

        // Parametre obligatoire absent -> BindingException
        Method mCheck = EmployeBindingController.class.getMethod("check", int.class);
        Parameter pCheckAge = mCheck.getParameters()[0];
        verifierThrow("bind(age requis absent) -> BindingException",
                () -> FrontControllerServlet.bind(new HashMap<>(), pCheckAge));

        // Valeur invalide pour int -> BindingException
        Map<String, String> paramsInvalides = new HashMap<>();
        paramsInvalides.put("nom", "Rakoto");
        paramsInvalides.put("age", "vingt");
        paramsInvalides.put("salaire", "100");
        verifierThrow("bind(age='vingt') -> BindingException",
                () -> FrontControllerServlet.bind(paramsInvalides, pAge));

        System.out.println("--- 5) SPRINT 7 BIS : bindObject (objet complexe) ---");

        // Remplissage d'un Employe via les SETTERS
        Map<String, String> paramsEmploye = new HashMap<>();
        paramsEmploye.put("nom", "Rakoto");
        paramsEmploye.put("poste", "Developpeur");
        paramsEmploye.put("age", "28");
        Employe emp = (Employe) FrontControllerServlet.bindObject(Employe.class, paramsEmploye);
        verifier("bindObject(Employe).nom=Rakoto (setter)", emp.getNom(), "Rakoto");
        verifier("bindObject(Employe).poste=Developpeur (setter)", emp.getPoste(), "Developpeur");
        verifier("bindObject(Employe).age=28 (setter)", emp.getAge(), 28);

        // Remplissage d'une Personne SANS setter -> ACCES DIRECT par reflexion
        Map<String, String> paramsPersonne = new HashMap<>();
        paramsPersonne.put("nom", "Bema");
        paramsPersonne.put("age", "31");
        Personne p = (Personne) FrontControllerServlet.bindObject(Personne.class, paramsPersonne);
        verifier("bindObject(Personne).nom=Bema (acces direct)", p.getNom(), "Bema");
        verifier("bindObject(Personne).age=31 (acces direct)", p.getAge(), 31);

        // Valeurs par defaut : champs absents de la requete
        Personne pVide = (Personne) FrontControllerServlet.bindObject(
                Personne.class, new HashMap<>());
        verifier("bindObject(Personne) absent: nom=null", pVide.getNom(), null);
        verifier("bindObject(Personne) absent: age=0", pVide.getAge(), 0);

        // Champ NON simple (List) ignore -> reste null
        Map<String, String> paramsList = new HashMap<>();
        paramsList.put("nom", "Rakoto");
        paramsList.put("competences", "Java,SQL");
        Personne pList = (Personne) FrontControllerServlet.bindObject(Personne.class, paramsList);
        verifier("bindObject(champ List ignore)=null", pList.getCompetences(), null);

        // Absence de constructeur sans argument -> BindingException
        verifierThrow("bindObject(sans constructeur) -> BindingException",
                () -> FrontControllerServlet.bindObject(NoCtorEmploye.class, new HashMap<>()));

        System.out.println("====================================");
        System.out.println("RESULTATS : " + ok + " OK, " + ko + " ERREUR(S)");
        if (ko == 0) {
            System.out.println("[SUCCES] Tous les tests du data binding sont valides !");
        } else {
            System.out.println("[ECHEC] Des tests ont echoue, corrige le framework !");
        }
    }

    private static void verifier(String nom, Object obtenu, Object attendu) {
        boolean egal;
        if (attendu == null) {
            egal = (obtenu == null);
        } else if (attendu instanceof Double) {
            egal = (obtenu instanceof Double)
                    && Math.abs((Double) obtenu - (Double) attendu) < 0.0001;
        } else {
            egal = attendu.equals(obtenu);
        }
        if (egal) {
            ok++;
            System.out.println("[OK] " + nom);
        } else {
            ko++;
            System.out.println("[ERREUR] " + nom + " -> obtenu : " + obtenu + ", attendu : " + attendu);
        }
    }

    private interface Action {
        void executer() throws Exception;
    }

    // Classe SANS constructeur sans argument -> bindObject doit lever BindingException
    private static class NoCtorEmploye {
        private String nom;

        NoCtorEmploye(int interdit) {
        }
    }

    private static void verifierThrow(String nom, Action action) {
        try {
            action.executer();
            ko++;
            System.out.println("[ERREUR] " + nom + " -> aucune exception levee !");
        } catch (BindingException e) {
            ok++;
            System.out.println("[OK] " + nom + " (message : " + e.getMessage() + ")");
        } catch (Exception e) {
            ko++;
            System.out.println("[ERREUR] " + nom + " -> mauvaise exception : " + e.getClass().getSimpleName());
        }
    }
}