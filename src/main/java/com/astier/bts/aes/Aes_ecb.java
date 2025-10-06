package com.astier.bts.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * The type Aes 128. ecb(Electronic Code Book)
 * pour cbc(Cipher Block Chaining) il faut IV vecteru init en plus)
 */
public class Aes_ecb {

    byte[] motDePasse;
    SecretKeySpec specification;
    int formatAes;

    public Aes_ecb(int formatAes,byte[] motDePasse) {
        this.formatAes = formatAes;
        {
            this.motDePasse = motDePasse;
            this.specification = new SecretKeySpec(motDePasse, "AES");
        }
    }

    public byte[] cryptage(byte[] aCoder){
        byte[] bytes = null;
        try {
            Cipher chiffreur = Cipher.getInstance("AES/ECB/PKCS5Padding"); //instancie un objet Cipher qui utilisera l'algorithme AES, PKCS5Padding : C'est le schéma de remplissage (padding) utilisé.
            chiffreur.init(Cipher.ENCRYPT_MODE, specification); // en chiffrement avec la clef (specification)
            bytes = chiffreur.doFinal(aCoder);//doFinal effectue le chiffrement sur les données aCoder.
        } catch(Exception e) {
            System.out.println("Erreur de cryptage");
        }
        return  bytes;
    }

    public byte[] decryptage (byte[] aDecoder){
        byte[] bytes = null;
        try {
            Cipher dechiffreur = Cipher.getInstance("AES/ECB/PKCS5Padding");//instancie un objet Cipher qui utilisera l'algorithme AES
            dechiffreur.init(Cipher.DECRYPT_MODE, specification); // en déchiffrement avec la clef (specification)
            bytes = dechiffreur.doFinal(aDecoder);
        } catch(Exception e) {
            System.out.println("Erreur de décryptage");
        }
        return bytes;
    }

}
