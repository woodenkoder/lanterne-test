# Runbook · mettre la Lanterne en production

## Mise en production

1. Vérifie que `main` est verte dans la CI.
2. Pose le tag : `git tag -a v1.4.0 -m "v1.4.0" && git push origin v1.4.0`.
3. Le workflow « Déployer » construit l'image une fois, la publie avec le tag, puis la déploie.

## Vérifications

- Le workflow est vert : la santé (`/actuator/health`) répond `UP` et la commande de test passe.
- Dans les 15 minutes : taux d'erreurs 5xx et latence des commandes, dans les logs et les métriques.

## Retour arrière

- Automatique : si la santé ou la commande de test échoue, le workflow remet le tag précédent et échoue.
- À la main : `deploy/deployer.sh ghcr.io/<dépôt>:<tag-précédent>`, puis préviens l'équipe.
