#!/usr/bin/env bash
# Déploie une image, vérifie qu'elle tient, sinon remet la précédente :
#   deploy/deployer.sh <image> [image-précédente]
set -uo pipefail
cd "$(dirname "$0")"
image="$1"
precedente="${2:-}"
URL=http://localhost:8080

sain() { # /actuator/health répond UP (90 secondes au plus)
  for _ in $(seq 1 45); do
    curl -fsS "$URL/actuator/health" 2>/dev/null | grep -q '"status":"UP"' && return 0
    sleep 2
  done
  return 1
}

commande_de_test() {
  curl -fsS -o /dev/null -X POST "$URL/api/commandes" -H 'Content-Type: application/json' \
    -d '{"client":"test-deploiement@nocturne.example","lignes":[{"produitId":1,"quantite":1}]}'
}

if ./plateforme.sh lancer "$image" && sain && commande_de_test; then
  echo "✔ $image est en production."
  exit 0
fi
echo "::error::$image ne tient pas (santé ou commande de test) : retour à ${precedente:-aucune version}."
if [ -n "$precedente" ] && ./plateforme.sh lancer "$precedente" && sain; then
  echo "↩ $precedente est de nouveau en production."
fi
exit 1
