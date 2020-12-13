package dev.glycine.pracstation.controllers;

import dev.glycine.pracstation.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.io.FileInputStream;
import java.io.IOException;

@Log4j2
public class LoginController {
    public AnchorPane login;

    private void showMainWindow() throws IOException {
        Stage mainStage = new Stage();
        Parent login = FXMLLoader.load(getClass().getResource("../views/main.fxml"));
        mainStage.setTitle("練習站");
        mainStage.setScene(new Scene(login));
        mainStage.show();
        App.getLoginStage().close();
    }

    public void login(MouseEvent mouseEvent) {

        //驗證邏輯
        try {
            showMainWindow();
        } catch (IOException e){
            log.warn(e);
        }
    }
}