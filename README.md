# AES Java – ECB / CBC

## Contexte

Projet réalisé en BTS CIEL pour comprendre concrètement le fonctionnement du chiffrement symétrique AES en Java.

- L’objectif était de manipuler moi-même :
- la gestion des clés (SecretKeySpec)
- le vecteur d’initialisation (IV)
- les modes ECB et CBC
- la manipulation des tableaux de bytes
- le chiffrement et le déchiffrement


## Fonctionnement

- Lecture du mot de passe et de l’IV depuis un fichier JSON
- Normalisation de la clé à 256 bits (32 bytes)
- Génération des objets :
   - SecretKeySpec
   - IvParameterSpec
     
- Implémentation AES :
   - BCE →AES/ECB/PKCS5Padding
   - Radio-Canada →AES/CBC/PKCS5Padding
 
    
- Utilisation de Cipher :
   - ENCRYPT_MODE
   - DECRYPT_MODE
  
- Test console avec affichage :
   - texte original
   - données chiffrées (hexadécimal)
   - texte déchiffré

## Chiffrement / Déchiffrement

- Conversion du texte en UTF-8 (byte[])
- Chiffrement via Cipher.doFinal()
- Les données chiffrées sont binaires
- Déchiffrement avec la même clé (et IV en CBC)
- Vérification que le texte récupéré correspond à l’original


## Ce que j’ai compris

- Une donnée chiffrée n’est pas du texte lisible mais du binaire
- Le mode ECB est simple mais moins sécurisé
- Le mode CBC nécessite un vecteur d’initialisation
- AES fonctionne par blocs de 128 bits
- La clé doit respecter une taille précise (128 / 192 / 256 bits)
- Le mode utilisé pour chiffrer doit être identique pour déchiffrer


## Technologies

- Java 21
- javax.crypto
- Gson
- Gradle
