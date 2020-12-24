package dev.glycine.pracstation.controllers;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.jfoenix.assets.JFoenixResources;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import dev.glycine.pracstation.models.AppleColor;
import javafx.scene.control.Label;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import dev.glycine.pracstation.App;

import dev.glycine.pracstation.service.TokenManager;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import lombok.extern.log4j.Log4j2;
import org.kordamp.ikonli.javafx.FontIcon;

import static com.sun.javafx.scene.control.skin.Utils.getResource;
import static io.grpc.Status.Code.*;


@Log4j2
public class LoginController {
    public static final String PACKAGE_PATH = "/dev/glycine/pracstation/";

    public GridPane login;
    public JFXPasswordField password;
    public JFXTextField username;
    public JFXButton loginBtn;
    public Label warnText;

    @FXML
    public void initialize() {
        RequiredFieldValidator validator = new RequiredFieldValidator("用戶名不得留空");
        FontIcon warnIcon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);
        warnIcon.getStyleClass().add("error");
        validator.setIcon(warnIcon);

        username.getValidators().add(validator);
        username.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                username.validate();
            }
        });

        validator = new RequiredFieldValidator("密碼不得留空");
        warnIcon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);
        warnIcon.getStyleClass().add("error");
        validator.setIcon(warnIcon);

        password.getValidators().add(validator);
        password.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                password.validate();
            }
        });
    }

    Status.Code statusCode;

    @SuppressWarnings("unused")
    public void login(MouseEvent mouseEvent) {
        if (username.getText().length() == 0 || password.getText().length() == 0) {
            username.validate();
            password.validate();
            return;
        }

        ValidatorBase userNotFoundValidator = new ValidatorBase("無此用戶") {
            @Override
            protected void eval() {
                hasErrors.set(statusCode == NOT_FOUND);
            }
        };
        FontIcon warnIcon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);
        warnIcon.getStyleClass().add("error");
        userNotFoundValidator.setIcon(warnIcon);
        username.getValidators().add(userNotFoundValidator);

        ValidatorBase abortedValidator = new ValidatorBase("密碼錯誤") {
            @Override
            protected void eval() {
                hasErrors.set(statusCode == ABORTED);
            }
        };
        warnIcon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);
        warnIcon.getStyleClass().add("error");
        abortedValidator.setIcon(warnIcon);
        password.getValidators().add(abortedValidator);

        TokenManager manager = new TokenManager();
        try {
            var str = manager.getClient().login(username.getText().trim(), password.getText());
            manager.setToken(str);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.UNAVAILABLE) {
                warnText.setTextFill(AppleColor.RED);
                warnText.setText("網路無法連結");
            }
            statusCode = e.getStatus().getCode();
            username.validate();
            password.validate();
            statusCode = null;
            log.warn(e);
            return;
        }

        try {
            Stage mainStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PACKAGE_PATH + "views/main.fxml"));
            Parent login = loader.load();
            Scene scene = new Scene(login);
            scene.getStylesheets().addAll(
                    JFoenixResources.load("css/jfoenix-fonts.css").toExternalForm(),
                    JFoenixResources.load("css/jfoenix-design.css").toExternalForm(),
                    JFoenixResources.load(PACKAGE_PATH + "views/css/jfoenix-components.css").toExternalForm()
            );
            mainStage.setTitle("練習站");
            mainStage.setScene(scene);
            mainStage.show();
            App.getLoginStage().close();
            loader.setController(StationController.getInstance());
            StationController.getInstance().init(manager.getToken());
            mainStage.setOnCloseRequest(e -> {
                try {
                    manager.getClient().shutdown();
                } catch (InterruptedException interruptedException) {
                    log.error(interruptedException);
                }
            });
        } catch (IOException e) {
            log.error(e);
        }
    }
}
