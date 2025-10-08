package

import com.astier.bts.aes.Aes_cbc;
import com.astier.bts.aes.Aes_ecb;
import com.astier.bts.aes.Outils;
import com.astier.bts.configuration.Configuration_json;
import com.astier.bts.configuration.Record_config;

import java.nio.charset.StandardCharsets;

public class CryptoClient {
    public enum Mode { NONE, ECB, CBC }

    private final Mode mode;
    private final Aes_ecb aesEcb;
    private final Aes_cbc aesCbc;

    public CryptoClient(Mode mode) {
        this.mode = mode;
        if (mode == Mode.NONE) { aesEcb = null; aesCbc = null; return; }

        int formatAESKey = 32, formatAESIV = 16;
        Record_config cfg = Configuration_json.init();
        if (cfg == null) throw new IllegalStateException("configuration_json.json introuvable");

        byte[] key = Outils.normalizeChaine(cfg.motDePasse(), formatAESKey);
        byte[] iv  = Outils.normalizeChaine(cfg.iv(),         formatAESIV);

        aesEcb = (mode == Mode.ECB) ? new Aes_ecb(formatAESKey, key) : null;
        aesCbc = (mode == Mode.CBC) ? new Aes_cbc(key, iv)          : null;
    }

    public boolean enabled() { return mode != Mode.NONE; }

    public byte[] encrypt(String clearText) {
        byte[] plain = clearText.getBytes(StandardCharsets.UTF_8);
        if (mode == Mode.ECB) return aesEcb.cryptage(plain);
        if (mode == Mode.CBC) return aesCbc.cryptage(plain);
        return plain;
    }

    public String decrypt(byte[] cipher) {
        byte[] plain;
        if (mode == Mode.ECB) plain = aesEcb.decryptage(cipher);
        else if (mode == Mode.CBC) plain = aesCbc.decryptage(cipher);
        else plain = cipher;
        return new String(plain, StandardCharsets.UTF_8);
    }
}
