package dev.glycine.pracstation;

import dev.glycine.pracstation.models.Station;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class App extends Application {
    @Getter
    private static Stage loginStage;

    public static void main(String[] args) {
        new Station();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        loginStage = primaryStage;
        Parent login = FXMLLoader.load(getClass().getResource("views/login.fxml"));
        primaryStage.setTitle("pracstation");
        primaryStage.setScene(new Scene(login));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
