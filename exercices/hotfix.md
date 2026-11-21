# Bug en production : les prix TTC sont faux

> **De :** la comptabilité
> **Version touchée :** v0.1.0

Depuis la v0.1.0, les prix TTC sont presque égaux aux prix HT : un article à 100,00 € HT est affiché
102,00 € TTC au lieu de 120,00 €. La TVA est à 20 %.

Le calcul est dans `com.nocturne.lanterne.prix.Tva`. Corrige-le **avec un test** (`TvaTest`) qui prouve
que 100,00 € HT donnent 120,00 € TTC, sur une branche `hotfix/0.1.1` partie du tag `v0.1.0`.
