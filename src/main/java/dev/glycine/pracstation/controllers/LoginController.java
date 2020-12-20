package dev.glycine.pracstation.controllers;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import dev.glycine.pracstation.components.AppleColor;
import javafx.scene.control.Label;
import javafx.scene.text.TextFlow;
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

    private void showMainWindow() throws IOException {
        Stage mainStage = new Stage();
        Parent login = FXMLLoader.load(getClass().getResource(PACKAGE_PATH + "views/main.fxml"));
        mainStage.setTitle("練習站");
        mainStage.setScene(new Scene(login));
        mainStage.show();
        App.getLoginStage().close();
    }

    Status.Code statusCode;

    @SuppressWarnings("unused")
    public void login(MouseEvent mouseEvent) {
        if (username.getText().length() == 0 || password.getText().length() == 0){
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
                hasErrors.set( statusCode == ABORTED);
            }
        };
        warnIcon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);
        warnIcon.getStyleClass().add("error");
        abortedValidator.setIcon(warnIcon);
        password.getValidators().add(abortedValidator);

        try {
            var str = TokenManager.getClient().login(username.getText().trim(), password.getText());
            TokenManager.setToken(str);
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

        TokenManager.getExecutor().scheduleAtFixedRate(() -> {
            try {
                var str = TokenManager.getClient().login(username.getText().trim(), password.getText());
                log.info("refreshed");
                TokenManager.setToken(str);
            } catch (StatusRuntimeException e) {
                log.warn(e);
            }
        }, 1L, 1L, TimeUnit.MINUTES);

        try {
            showMainWindow();
        } catch (IOException e) {
            log.error(e);
        }
    }
}
