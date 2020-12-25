package dev.glycine.pracstation.models;

import dev.glycine.pracstation.controllers.MainController;
import javafx.animation.FillTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

@Log4j2
public class RouteBadge extends StackPane {
    private static final int FADE_DURATION = 150;
    private static final Color FOCUSED_COLOR = Color.rgb(255, 69, 58, 0.5);

    private final Rectangle background = new Rectangle();

    @Getter
    private final String routeId;

    @Getter
    private final Light protectionLight;

    private final FocusedProperty focusedProperty = new FocusedProperty(false);

    public boolean isBadgeFocused() {
        return focusedProperty.get();
    }

    public RouteBadge(String text, Light light) {
        routeId = text;
        this.protectionLight = light;
        this.focusedProperty.bind(light.getFocusedProperty());
        this.focusedProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue) background.setFill(FOCUSED_COLOR);
            else background.setFill(AppleColor.GRAY);
        });

        background.getStyleClass().add("badge-background");
        background.heightProperty().bind(heightProperty());
        background.widthProperty().bind(widthProperty());

        var start = text.split("-")[0];
        var end = text.split("-")[1];
        Label startLabel = new Label(start);
        Label endLabel = new Label(end);

        startLabel.getStyleClass().add("badge-label");
        endLabel.getStyleClass().add("badge-label");

        FontIcon warnIcon = new FontIcon(FontAwesomeSolid.TIMES_CIRCLE);
        warnIcon.setIconColor(AppleColor.GRAY_6);
        FontIcon arrow = new FontIcon(FontAwesomeSolid.ARROW_RIGHT);
        arrow.setIconColor(AppleColor.GRAY_6);

        var box = new HBox(startLabel, arrow, endLabel, warnIcon);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(2.5, 5, 2.5, 5));

        var fillRed = new FillTransition(Duration.millis(FADE_DURATION), background);

        fillRed.setToValue(AppleColor.RED);

        var fillGray = new FillTransition(Duration.millis(FADE_DURATION), background);
        fillGray.setFromValue(AppleColor.RED);

        setOnMouseEntered(e -> {
            if (isBadgeFocused()) {
                fillRed.setFromValue(FOCUSED_COLOR);
            } else {
                fillRed.setFromValue(AppleColor.GRAY);
            }
            fillRed.play();
        });

        setOnMouseExited(e -> {
            if (isBadgeFocused()) {
                fillGray.setToValue(FOCUSED_COLOR);
            } else {
                fillGray.setToValue(AppleColor.GRAY);
            }
            fillGray.play();
        });
        setOnMouseClicked(MainController.getInstance()::handleCancelRoute);

        background.arcHeightProperty().bind(heightProperty());
        background.arcWidthProperty().bind(heightProperty());
        getChildren().addAll(background, box);
    }
}
