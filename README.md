# Application de test - Mini Spring Framework

Application web utilisée pour tester le framework `mini-spring-framework`.

## SPRINT0
- Déploiement sur Tomcat
- Le mini-spring.jar est placé dans WEB-INF/lib/
- Toutes les requêtes passent par le FrontControllerServlet déclaré dans web.xml

## SPRINT10
- `AuthController` : URLs publiques, connexion, déconnexion et action protégée
- `@Authentified` sur `/auth/action` et `/auth/api/action`
- `<init-param>` `sessionVariableName=utilisateur` dans `web.xml`
- Sans connexion : réponse 401 (HTML ou JSON), avec connexion : action exécutée