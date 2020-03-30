package dev.glycine.pracstation;

import dev.glycine.pracstation.models.Station;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class App extends Application {
    public static void main(String[] args) {
        log.warn("info");
        Station station = new Station();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/main.fxml"));
        primaryStage.setTitle("練習站");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
