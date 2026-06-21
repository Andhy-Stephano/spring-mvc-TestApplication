package test;

import annotation.Controller;

public class MainTest {
    public static void main(String[] args) {
        Class<?> classe1 = TestController1.class;
        Class<?> classe2 = TestController2.class;

        System.out.println("=== TEST DE DETECTION ===");

        // Vérification pour la classe 1
        if (classe1.isAnnotationPresent(Controller.class)) {
            System.out.println("[OK] TestController1 possede bien l'annotation @Controller");
        } else {
            System.out.println("[ERREUR] TestController1 n'a pas ete detecte !");
        }

        // Vérification pour la classe 2
        if (classe2.isAnnotationPresent(Controller.class)) {
            System.out.println("[ERREUR] TestController2 a l'annotation alors qu'il ne devrait pas !");
        } else {
            System.out.println("[OK] TestController2 est bien ignore (pas d'annotation)");
        }
    }
}