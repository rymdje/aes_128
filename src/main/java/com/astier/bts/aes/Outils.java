package com.astier.bts.aes;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

public class Outils {
    public static byte[] normalizeChaine(String chaine, int longeur) {
        Random r = new Random();
        ArrayList<Byte> ar = new ArrayList<>();
        byte[] b = chaine.getBytes(StandardCharsets.UTF_8);
        for (byte x : b) ar.add(x);
        while (ar.size() < longeur) {
            ar.add((byte) (r.nextInt(0x100))); // 0..255
        }
        return convertionEnBytes(ar, longeur);
    }

    public static byte[] convertionEnBytes(ArrayList<Byte> ar, int longeur){
        byte[] out = new byte[longeur];
        for (int i = 0; i < longeur; i++) out[i] = ar.get(i);
        return out;
    }
}
