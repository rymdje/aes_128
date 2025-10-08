package com.astier.bts.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Aes_cbc {
    byte[] motDePasse;
    SecretKeySpec specification;
    IvParameterSpec iv;

    public Aes_cbc(byte[] motDePasse, byte[] iv) {
        this.motDePasse = motDePasse;
        this.specification = new SecretKeySpec(motDePasse,"AES");
        this.iv = new IvParameterSpec(iv);
    }

    public byte[] cryptage(byte[] aCoder){
        try {
            Cipher chiffreur = Cipher.getInstance("AES/CBC/PKCS5Padding");
            chiffreur.init(Cipher.ENCRYPT_MODE, specification,iv);
            return chiffreur.doFinal(aCoder);
        } catch(Exception e) {
            System.out.println("Erreur de cryptage");
            return null;
        }
    }

    public byte[] decryptage (byte[] aDecoder){
        try {
            Cipher dechiffreur = Cipher.getInstance("AES/CBC/PKCS5Padding");
            dechiffreur.init(Cipher.DECRYPT_MODE, specification,iv);
            return dechiffreur.doFinal(aDecoder);
        } catch(Exception e) {
            System.out.println("Erreur de décryptage");
            return null;
        }
    }
}
