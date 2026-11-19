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
| `exercice/validation` | 03.3 · La validation sur un DTO |

Les autres branches arrivent au fil des mises à jour du manoir.

## Règles

- Ne modifie pas `.github/workflows/manoir.yml` : le manoir n'accepte que les résultats de son propre workflow.
- Aucun secret dans le code : les clés vont dans les secrets GitHub ou les variables d'environnement.
