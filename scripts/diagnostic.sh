#!/usr/bin/env bash
# Diagnostic de la Lanterne, sans rien modifier : disque, mémoire, santé de l'API, dernières erreurs.
# Usage : scripts/diagnostic.sh [adresse de l'API] [fichier de logs]
set -euo pipefail

adresse="${1:-http://localhost:8080}"
logs="${2:-}"

echo "== Disque"
utilise=$(df -P / | awk 'NR == 2 { gsub("%", "", $5); print $5 }')
echo "Utilisé : ${utilise} %"
if [ "$utilise" -gt 80 ]; then
  echo "ATTENTION : le disque est utilisé à plus de 80 %"
fi

echo "== Mémoire"
if command -v free >/dev/null; then
  free -h | awk 'NR == 2 { print "Mémoire libre : " $7 " sur " $2 }'
else
  echo "Mémoire libre : $(awk '/MemAvailable/ { print int($2 / 1024) " Mo" }' /proc/meminfo)"
fi

echo "== Santé de l'API ($adresse)"
if reponse=$(curl -fsS --max-time 5 "$adresse/actuator/health"); then
  echo "$reponse"
else
  echo "ATTENTION : l'API ne répond pas"
fi

if [ -n "$logs" ]; then
  echo "== 20 dernières erreurs ($logs)"
  grep -E "ERROR" "$logs" | tail -n 20 || echo "Aucune erreur."
fi
