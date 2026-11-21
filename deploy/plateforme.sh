#!/usr/bin/env bash
# La plateforme de production de Nocturne, simulée sur la machine de la CI : un conteneur « lanterne-prod », sur le
# port 8080. Ne la modifie pas : le manoir utilise la sienne.
#   deploy/plateforme.sh lancer <image>   remplace la version en production par cette image
#   deploy/plateforme.sh image            affiche l'image en production
set -euo pipefail
case "${1:-}" in
  lancer)
    docker rm -f lanterne-prod >/dev/null 2>&1 || true
    docker run -d --name lanterne-prod -p 8080:8080 "$2" >/dev/null
    echo "plateforme : $2 lancée"
    ;;
  image)
    docker inspect -f '{{.Config.Image}}' lanterne-prod 2>/dev/null || echo aucune
    ;;
  *)
    echo "usage : deploy/plateforme.sh lancer <image> | image" >&2
    exit 2
    ;;
esac
