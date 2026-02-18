package com.astier.bts.tcp;

import com.astier.bts.client_tcp_prof.crypto.CryptoClient;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.*;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.LIME;
import static javafx.scene.paint.Color.RED;

public class HelloController implements Initializable {

    public Button button;
    public Button connecter;
    public Button deconnecter;

    public TextField TextFieldIP;
    public TextField TextFieldPort;
    public TextField TextFieldRequette;

    public Circle voyant;
    public TextArea TextAreaReponses;

    public static TCP tcp;
    public static boolean enRun = false;

    private String adresse, port;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        voyant.setFill(RED);

        button.setOnMouseClicked(e -> {
            try { envoyer(); }
            catch (Exception ex) { TextAreaReponses.appendText(ex + "\n"); }
        });

        connecter.setOnMouseClicked(e -> {
            try { connecter(); }
            catch (Exception ex) { TextAreaReponses.appendText(ex + "\n"); }
        });

        deconnecter.setOnMouseClicked(e -> {
            try { deconnecter(); }
            catch (Exception ex) { TextAreaReponses.appendText(ex + "\n"); }
        });

        try { setServeurUDP(); } catch (Exception ignored) {}
    }

    private void setServeurUDP() throws IOException, InterruptedException {
        InetAddress inetAddress = InetAddress.getByName("224.0.0.250");
        int portTCP = 5555, portUDP = 5556;
        byte ttl = 60;

        byte[] data = "Tu es qui?".getBytes();
        byte[] bufferResponse = new byte[512];

        MulticastSocket ms = new MulticastSocket();
        DatagramSocket dsResponse = new DatagramSocket(portUDP);
        DatagramPacket dpResponse = new DatagramPacket(bufferResponse, bufferResponse.length);

        new Thread(() -> {
            try { dsResponse.receive(dpResponse); }
            catch (IOException ignored) {}
        }).start();

        ms.setTimeToLive(ttl);
        DatagramPacket dp = new DatagramPacket(data, data.length, inetAddress, portTCP);
        ms.send(dp);

        Thread.sleep(100);
        ms.close();

        recupererDesParametres(dpResponse);
        dsResponse.close();
    }

    private void recupererDesParametres(DatagramPacket dp) {
        String s = new String(dp.getData(), 0, dp.getLength());
        String[] p = s.split(";");
        if (p.length >= 2) {
            TextFieldIP.setText(p[0]);
            TextFieldPort.setText(p[1]);
        }
    }

    private void envoyer() throws IOException {
        if (tcp != null) tcp.requette(TextFieldRequette.getText());
        TextFieldRequette.clear();
    }

    private void deconnecter() {
        if (enRun && tcp != null) {
            enRun = false;
            tcp.deconnection();
            voyant.setFill(RED);
        }
    }

    private void connecter() throws Exception {
        adresse = TextFieldIP.getText();
        port = TextFieldPort.getText();

        if (!adresse.isEmpty() && !port.isEmpty()) {
            InetAddress host = InetAddress.getByName(adresse);

            // Ici tu choisis le mode : NONE / ECB / CBC
            tcp = new TCP(host, Integer.parseInt(port), this, CryptoClient.Mode.CBC);

            tcp.connection();
            voyant.setFill(LIME);
            enRun = true;
        }
    }
}
