# AES Java – ECB / CBC

## Contexte

Projet réalisé pour comprendre concrètement le fonctionnement du chiffrement symétrique AES.

L’objectif était de manipuler moi-même :

- la gestion des clés
- les modes ECB et CBC
- les tableaux de bytes
- le chiffrement / déchiffrement


## Fonctionnement

- Lecture du mot de passe et de l’IV depuis un fichier JSON
- Normalisation de la clé à 256 bits
- Implémentation AES :
  - ECB (AES/ECB/PKCS5Padding)
  - CBC (AES/CBC/PKCS5Padding)
- Test console avec affichage :
  - texte original
  - données chiffrées (hex)
  - texte déchiffré


## Ce que j’ai compris

- Une donnée chiffrée n’est pas du texte mais du binaire
- L’importance du vecteur d’initialisation en CBC
- La différence structurelle entre ECB et CBC
- La gestion correcte des tailles de blocs AES


## Technologies

- Java 21
- javax.crypto
- Gson
- Gradle
