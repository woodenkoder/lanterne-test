#!/usr/bin/env bash
# Combat 01.3 (la Crypte) : prépare deux branches qui modifient la même ligne, dans TON dépôt.
#
# exercice/seuil-livraison : livraison gratuite dès 50 €.
# exercice/livraison-premium : livraison gratuite pour les clients premium.
# Toutes deux changent la même ligne de FraisPort.frais : un conflit t'attend.
set -euo pipefail

cd "$(git rev-parse --show-toplevel)"
if [ -n "$(git status --porcelain)" ]; then
  echo "Ton dépôt a des modifications en cours : commit-les ou mets-les de côté (git stash), puis relance."
  exit 1
fi
for branche in exercice/seuil-livraison exercice/livraison-premium; do
  if git show-ref --quiet "refs/heads/$branche"; then
    echo "La branche $branche existe déjà : supprime-la (git branch -D $branche) pour repartir de zéro."
    exit 1
  fi
done

frais=src/main/java/com/nocturne/lanterne/frais/FraisPort.java
tests=src/test/java/com/nocturne/lanterne/frais
if ! grep -q "        return STANDARD;" "$frais"; then
  echo "$frais a déjà été modifié : ce combat part de la version d'origine (return STANDARD;)."
  exit 1
fi
# frais_port <constantes en plus> <ligne de calcul> : réécrit FraisPort.java (portable : Linux, macOS, Windows).
frais_port() {
  cat >"$frais" <<JAVA
package com.nocturne.lanterne.frais;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/** Les frais de port d'une commande. */
@Component
public class FraisPort {

    static final BigDecimal STANDARD = new BigDecimal("4.90");
$1
    public BigDecimal frais(BigDecimal montant, boolean premium) {
        $2
    }
}
JAVA
}

git switch --quiet main
git switch --quiet --create exercice/seuil-livraison
frais_port '    static final BigDecimal SEUIL = new BigDecimal("50.00");
' 'return montant.compareTo(SEUIL) >= 0 ? BigDecimal.ZERO : STANDARD;'
mkdir -p "$tests"
cat >"$tests/FraisPortSeuilTest.java" <<'JAVA'
package com.nocturne.lanterne.frais;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class FraisPortSeuilTest {

    FraisPort frais = new FraisPort();

    @Test
    void livraison_gratuite_des_50_euros() {
        assertThat(frais.frais(new BigDecimal("50.00"), false)).isEqualByComparingTo("0");
    }

    @Test
    void frais_standard_sous_50_euros() {
        assertThat(frais.frais(new BigDecimal("49.99"), false)).isEqualByComparingTo("4.90");
    }
}
JAVA
git add "$frais" "$tests"
git commit --quiet -m "feat: livraison gratuite dès 50 €"

git switch --quiet main
git switch --quiet --create exercice/livraison-premium
frais_port '' 'return premium ? BigDecimal.ZERO : STANDARD;'
mkdir -p "$tests"
cat >"$tests/FraisPortPremiumTest.java" <<'JAVA'
package com.nocturne.lanterne.frais;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class FraisPortPremiumTest {

    FraisPort frais = new FraisPort();

    @Test
    void livraison_gratuite_pour_les_clients_premium() {
        assertThat(frais.frais(new BigDecimal("12.00"), true)).isEqualByComparingTo("0");
    }

    @Test
    void frais_standard_pour_les_autres() {
        assertThat(frais.frais(new BigDecimal("12.00"), false)).isEqualByComparingTo("4.90");
    }
}
JAVA
git add "$frais" "$tests"
git commit --quiet -m "feat: livraison gratuite pour les clients premium"

git switch --quiet main
echo "C'est prêt : exercice/seuil-livraison et exercice/livraison-premium partent de main."
echo "Pousse-les : git push origin exercice/seuil-livraison exercice/livraison-premium"
echo "Puis : fusionne la première dans main, mets la seconde à jour avec main… et exorcise le conflit."
