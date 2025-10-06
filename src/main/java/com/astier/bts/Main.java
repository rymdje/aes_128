package com.astier.bts;

import com.astier.bts.aes.Aes_cbc;
import com.astier.bts.aes.Aes_ecb;
import com.astier.bts.aes.Outils;
import com.astier.bts.configuration.Configuration_json;
import com.astier.bts.configuration.Record_config;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static int formatAESKey=32;
    public static int formatAESIV=16;
    public static Record_config config= Configuration_json.init();
    public static Aes_ecb aesEcb = new Aes_ecb(formatAESKey,Outils.normalizeChaine(config.motDePasse(),formatAESKey));
    public static Aes_cbc aesCbc=new Aes_cbc(Outils.normalizeChaine(config.motDePasse(),formatAESKey),Outils.normalizeChaine(config.iv(), formatAESIV));
    public static Scanner sc =new Scanner(System.in);
    public static void main(String[] args) {
        byte[] bytesToAES = null;
        byte[] bytesFromAES = null;
        System.out.println("""
                pour info les paramétres sont les suivants:
                mot de passe:               %s
                vesteur d'initialisation:   %s
                """.formatted(new String(Outils.normalizeChaine(config.motDePasse(),formatAESKey), StandardCharsets.UTF_8),
                new String(Outils.normalizeChaine(config.iv(),formatAESIV))));


        System.out.println("""
                Vous informations à coder svp:             
                """);
        String data = sc.nextLine();

        //
        bytesToAES = aesEcb.cryptage(data.getBytes(StandardCharsets.UTF_8));
        System.out.println(new String(bytesToAES, StandardCharsets.UTF_8)+"\n");
        bytesFromAES= aesEcb.decryptage(bytesToAES);
        System.out.println(new String(bytesFromAES, StandardCharsets.UTF_8));
        System.out.println("Encoder");
        for (byte b : bytesToAES) {
            System.out.printf("%02X ", b);  // %02X pour afficher deux chiffres avec un z?ro initial si n?cessaire
        }
        System.out.println("\nDécoder");
        for (byte b : bytesFromAES) {
            System.out.printf("%02X ", b);  // %02X pour afficher deux chiffres avec un z?ro initial si n?cessaire
        }


        System.out.println("""
                
                #####################################CBC###############################             
                """);
        bytesToAES = aesCbc.cryptage(data.getBytes(StandardCharsets.UTF_8));
        System.out.println(new String(bytesToAES, StandardCharsets.UTF_8)+"\n");
        bytesFromAES= aesCbc.decryptage(bytesToAES);
        System.out.println(new String(bytesFromAES, StandardCharsets.UTF_8));
        System.out.println("Encoder");
        for (byte b : bytesToAES) {
            System.out.printf("%02X ", b);  // %02X pour afficher deux chiffres avec un z?ro initial si n?cessaire
        }
        System.out.println("\nDécoder");
        for (byte b : bytesFromAES) {
            System.out.printf("%02X ", b);  // %02X pour afficher deux chiffres avec un z?ro initial si n?cessaire
        }

        while (!sc.nextLine().equalsIgnoreCase("exit")){

        }
    }
}