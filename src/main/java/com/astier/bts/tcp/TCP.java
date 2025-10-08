package com.astier.bts.client_tcp_prof.tcp;

import com.astier.bts.client_tcp_prof.HelloController;
import com.astier.bts.client_tcp_prof.crypto.CryptoClient;
import com.astier.bts.client_tcp_prof.crypto.CryptoClient.Mode;
import javafx.application.Platform;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TCP extends Thread {
    private final InetAddress serveur;
    private final int port;
    private final HelloController ui;

    private Socket socket;
    private BufferedReader inLine;
    private PrintWriter outLine;
    private DataInputStream  inBin;
    private DataOutputStream outBin;
    private volatile boolean running = false;

    private final CryptoClient crypto;
    private final boolean useBinary;

    public TCP(InetAddress serveur, int port, HelloController ui, Mode cryptoMode) {
        this.serveur = serveur;
        this.port = port;
        this.ui = ui;
        this.crypto = new CryptoClient(cryptoMode);
        this.useBinary = crypto.enabled();
    }

    public void connection() {
        try {
            socket = new Socket(serveur, port);
            running = true;
            if (useBinary) {
                inBin  = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                outBin = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            } else {
                inLine  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                outLine = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            }
            start();
        } catch (IOException e) {
            post("Erreur de connexion TCP : " + e.getMessage());
        }
    }

    public void requette(String req) {
        try {
            if (!useBinary) { outLine.println(req); return; }
            byte[] cipher = crypto.encrypt(req);
            outBin.writeInt(cipher.length);
            outBin.write(cipher);
            outBin.flush();
        } catch (IOException e) {
            post("Erreur envoi TCP : " + e.getMessage());
        }
    }

    public void deconnection() {
        running = false;
        try { if (outLine != null) outLine.close(); } catch (Exception ignored) {}
        try { if (inLine  != null) inLine.close();  } catch (Exception ignored) {}
        try { if (outBin  != null) outBin.close();  } catch (Exception ignored) {}
        try { if (inBin   != null) inBin.close();   } catch (Exception ignored) {}
        try { if (socket != null && !socket.isClosed()) socket.close(); } catch (Exception ignored) {}
        post("TCP: déconnecté.");
    }

    @Override
    public void run() {
        try {
            if (!useBinary) {
                String line;
                while (running && (line = inLine.readLine()) != null) post(line);
                post("TCP: serveur a fermé la connexion.");
            } else {
                while (running) {
                    int len;
                    try { len = inBin.readInt(); } catch (EOFException eof) { break; }
                    if (len <= 0 || len > (1<<20)) { post("Trame invalide (len="+len+")"); break; }
                    byte[] data = inBin.readNBytes(len);
                    post(crypto.decrypt(data));
                }
            }
        } catch (IOException e) {
            post("TCP: lecture interrompue (" + e.getMessage() + ")");
        } finally {
            deconnection();
        }
    }

    private void post(String msg) {
        Platform.runLater(() -> ui.TextAreaReponses.appendText("SERVER > " + msg + "\n"));
    }
}

