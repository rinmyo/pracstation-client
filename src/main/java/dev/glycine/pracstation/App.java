package dev.glycine.pracstation;

import com.jfoenix.assets.JFoenixResources;
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
    public static final String PACKAGE_PATH = "/dev/glycine/pracstation/";

    @Getter
    private static Stage loginStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        loginStage = primaryStage;
        Parent login = FXMLLoader.load(getClass().getResource(PACKAGE_PATH + "views/login.fxml"));
//        Parent login = FXMLLoader.load(getClass().getResource(PACKAGE_PATH + "views/main.fxml"));
        Scene scene = new Scene(login);
        scene.getStylesheets().addAll(
                JFoenixResources.load("css/jfoenix-fonts.css").toExternalForm(),
                JFoenixResources.load("css/jfoenix-design.css").toExternalForm(),
                JFoenixResources.load(PACKAGE_PATH + "views/css/jfoenix-components.css").toExternalForm()
        );
        primaryStage.setTitle("pracstation");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        log.info("Application has been launched.");
    }
}
