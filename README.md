# La Lanterne

L'API de gestion de commandes de **Nocturne**, que tu construis pièce après pièce dans le manoir de
**PROD READY · La Maison hantée de la prod**.

## Démarrer

1. En haut de cette page : **Use this template → Create a new repository**, en cochant **Include all branches**
   (chaque combat a sa branche `exercice/…`). Ton dépôt peut être public ou privé.
2. Dans le manoir : **Mon compte → Ton dépôt GitHub → Obtenir un code de liaison**.
3. Dans ton dépôt : **Settings → Secrets and variables → Actions → New repository secret**, nom `MANOIR_CODE`,
   valeur : le code (valable 1 heure, une seule fois).
4. Pousse sur une branche `exercice/…` : nos tests cachés tournent dans l'onglet **Actions**, et le résultat
   arrive dans le manoir.

## Lancer la Lanterne

Java 21 suffit (le wrapper Maven télécharge Maven) :

```bash
./mvnw spring-boot:run   # l'API sur http://localhost:8080
./mvnw verify            # tes tests
```

## Les combats

| Branche | Épreuve |
| --- | --- |
| `exercice/golem` | 03.1 · Sortir la logique du controller |
| `exercice/injection` | 03.2 · Passer à l'injection par constructeur |
| `exercice/validation` | 03.3 · La validation sur un DTO |
| `exercice/virement` | 03.4 · Corriger un virement enregistré à moitié |
| `exercice/configuration` | 03.5 · Variables d'environnement, profils dev et prod |
| `exercice/n-plus-1` | 04.3 · Corriger un N+1 avec un fetch join |
| `exercice/crud` | 05.1 · Un CRUD qui respecte les conventions |
| `exercice/erreurs` | 05.3 · Un @ControllerAdvice au format d'erreur standard |
| `exercice/versions` | 05.4 · Une v2 d'un endpoint en gardant la v1 |
| `exercice/doc-debit` | 05.5 · La doc Swagger et une limite de débit |
| `exercice/mockito` | 06.2 · Les tests unitaires d'un service avec un mock |
| `exercice/integration` | 06.4 · Un test d'intégration avec un vrai PostgreSQL |
| `exercice/bdd` | 06.5 · Un scénario Cucumber du parcours d'achat |
| `exercice/auth` | 07.2 · Hacher avec bcrypt et émettre un JWT |
| `exercice/autorisation` | 07.3 · Protéger les endpoints admin avec Spring Security |
| `exercice/secrets-cors` | 07.4 · Sortir les secrets du code et régler CORS |
| `exercice/docker` | 08.1 · Un Dockerfile multi-étapes |
| `exercice/compose` | 08.2 · Compose avec l'app, PostgreSQL et un volume |
| `exercice/ci` | 08.3 · Un workflow qui build et teste à chaque push |
| `exercice/qualite` | 08.4 · Ajouter l'analyse et bloquer la PR si elle échoue |
| `exercice/deploiement` | 08.5 · Un déploiement automatique sur tag |
| `exercice/diagnostic` | 08.6 · Un script Bash de diagnostic lancé par ta CI |

Les autres branches arrivent au fil des mises à jour du manoir.

## Règles

- Ne modifie pas `.github/workflows/manoir.yml` : le manoir n'accepte que les résultats de son propre workflow.
- Aucun secret dans le code : les clés vont dans les secrets GitHub ou les variables d'environnement.
