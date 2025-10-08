package com.astier.bts.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Aes_ecb {
    byte[] motDePasse;
    SecretKeySpec specification;
    int formatAes;

    public Aes_ecb(int formatAes, byte[] motDePasse) {
        this.formatAes = formatAes;
        this.motDePasse = motDePasse;
        this.specification = new SecretKeySpec(motDePasse, "AES");
    }

    public byte[] cryptage(byte[] aCoder){
        try {
            Cipher chiffreur = Cipher.getInstance("AES/ECB/PKCS5Padding");
            chiffreur.init(Cipher.ENCRYPT_MODE, specification);
            return chiffreur.doFinal(aCoder);
        } catch(Exception e) {
            System.out.println("Erreur de cryptage");
            return null;
        }
    }

    public byte[] decryptage (byte[] aDecoder){
        try {
            Cipher dechiffreur = Cipher.getInstance("AES/ECB/PKCS5Padding");
            dechiffreur.init(Cipher.DECRYPT_MODE, specification);
            return dechiffreur.doFinal(aDecoder);
        } catch(Exception e) {
            System.out.println("Erreur de décryptage");
            return null;
        }
    }
}
