#!/usr/bin/env bash
# Combat 01.2 (la Crypte) : prépare une branche en retard dans TON dépôt.
#
# Crée exercice/en-retard à partir de ton main : un commit de travail, puis deux commits de correction
# (« fix typo », « fix test »). Pendant ce temps, main avance d'un commit (le travail d'une collègue).
# Ensuite : git push origin main exercice/en-retard … et à toi de rebaser.
set -euo pipefail

cd "$(git rev-parse --show-toplevel)"
if [ -n "$(git status --porcelain)" ]; then
  echo "Ton dépôt a des modifications en cours : commit-les ou mets-les de côté (git stash), puis relance."
  exit 1
fi
if git show-ref --quiet refs/heads/exercice/en-retard; then
  echo "La branche exercice/en-retard existe déjà : supprime-la (git branch -D exercice/en-retard) pour repartir de zéro."
  exit 1
fi

git switch --quiet main
git switch --quiet --create exercice/en-retard

source_dir=src/main/java/com/nocturne/lanterne/bienvenue
test_dir=src/test/java/com/nocturne/lanterne/bienvenue
mkdir -p "$source_dir" "$test_dir"

ecrire_bienvenue() {
  cat >"$source_dir/Bienvenue.java" <<JAVA
package com.nocturne.lanterne.bienvenue;

import org.springframework.stereotype.Component;

/** Le message envoyé à un nouveau client de Nocturne. */
@Component
public class Bienvenue {

    public String message(String prenom) {
        return "$1 dans la boutique de Nocturne, " + prenom + " !";
    }
}
JAVA
}

ecrire_test() {
  cat >"$test_dir/BienvenueTest.java" <<JAVA
package com.nocturne.lanterne.bienvenue;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BienvenueTest {

    @Test
    void le_message_salue_le_client_par_son_prenom() {
        assertThat(new Bienvenue().message("Ada")).isEqualTo("$1 dans la boutique de Nocturne, Ada !");
    }
}
JAVA
}

ecrire_bienvenue "Bienvenu"
ecrire_test "Bienvenu"
git add "$source_dir" "$test_dir"
git commit --quiet -m "feat: le message de bienvenue des nouveaux clients"

ecrire_bienvenue "Bienvenue"
git add "$source_dir"
git commit --quiet -m "fix typo"

ecrire_test "Bienvenue"
git add "$test_dir"
git commit --quiet -m "fix test"

# Pendant ce temps, main avance : le travail d'une collègue.
git switch --quiet main
cat >>JOURNAL.md <<'MD'
# Le journal de la Lanterne

- Les frais de port et la TVA sont en place.
MD
git add JOURNAL.md
git commit --quiet -m "docs: le journal de la Lanterne"

echo "C'est prêt : exercice/en-retard a 3 commits, et main a avancé d'un commit depuis."
echo "Pousse les deux : git push origin main exercice/en-retard"
echo "Puis rebase exercice/en-retard sur main (fonds « fix typo » et « fix test »), et git push --force-with-lease."
