package dev.glycine.pracstation;

import dev.glycine.pracstation.models.Station;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;

public class App extends Application {
    public static final String PACKAGE_PATH = "/dev/glycine/pracstation/";

    @Getter
    private static Stage loginStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        new Station();
        loginStage = primaryStage;
        Parent login = FXMLLoader.load(getClass().getResource(PACKAGE_PATH + "views/login.fxml"));
        primaryStage.setTitle("pracstation");
        primaryStage.setScene(new Scene(login));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
