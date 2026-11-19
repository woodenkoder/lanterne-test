# Déployer la Lanterne

## Publier une version

```bash
git tag v1.2.0
git push origin v1.2.0
```

Le workflow « Déploiement » teste, construit l'image et la publie : `ghcr.io/<toi>/<ta-lanterne>:v1.2.0`.

## Retour arrière

On ne corrige pas en urgence : on revient à la version d'avant, qui marchait.

```bash
docker pull ghcr.io/<toi>/<ta-lanterne>:v1.1.0
docker compose up -d   # avec l'image v1.1.0 dans compose.yaml
```

## Ce qu'il faut surveiller après un déploiement

- `/actuator/health` répond `UP` ;
- les logs : pas de nouvelles erreurs (`docker compose logs -f api`) ;
- les métriques : temps de réponse et taux d'erreurs 5xx, comparés à avant le déploiement.
