package com.astier.bts.tcp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(
                getClass().getResource("hello-view.fxml")
        ));

        stage.setOnCloseRequest(event -> {
            try {
                if (HelloController.enRun && HelloController.tcp != null) {
                    HelloController.tcp.deconnection();
                }
            } catch (Exception ignored) {}
            System.exit(0);
        });

        stage.setTitle("TCP-Client MM");
        // Mets ton image dans src/main/resources/icone/index.jpg si tu veux que ça marche
        stage.getIcons().add(new Image("/icone/index.jpg"));

        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
