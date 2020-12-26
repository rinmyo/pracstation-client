package dev.glycine.pracstation.models;

import dev.glycine.pracstation.controllers.MainController;
import javafx.animation.FillTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

@Log4j2
public class RouteBadge extends StackPane {
    private static final int FADE_DURATION = 150;

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
            if (newValue) background.setFill(AppleColor.GRAY);
            else background.setFill(AppleColor.GRAY_2);
        });

        background.getStyleClass().add("badge-background");
        background.heightProperty().bind(heightProperty());
        background.widthProperty().bind(widthProperty());

        var start = text.split("-")[0];
        var end = text.split("-")[1];
        var startLabel = new Label(start);
        var endLabel = new Label(end);

        startLabel.getStyleClass().add("badge-label");
        endLabel.getStyleClass().add("badge-label");

        var arrow = new FontIcon(FontAwesomeSolid.ARROW_RIGHT);
        arrow.setIconColor(AppleColor.GRAY_6);

        var box = new HBox(startLabel, arrow, endLabel);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(2.5, 5, 2.5, 5));

        var enterFill = new FillTransition(Duration.millis(FADE_DURATION), background);
        var exitFill = new FillTransition(Duration.millis(FADE_DURATION), background);
        enterFill.setFromValue(AppleColor.GRAY_2);
        enterFill.setToValue(AppleColor.GRAY);
        exitFill.setFromValue(AppleColor.GRAY);
        exitFill.setToValue(AppleColor.GRAY_2);

        setOnMouseEntered(e -> {
            if (!isBadgeFocused()) {
                enterFill.play();
            }
        });

        setOnMouseExited(e -> {
            if (!isBadgeFocused()) {
                exitFill.play();
            }
        });
        setOnMouseClicked(MainController.getInstance()::handleClickRoute);

        background.arcHeightProperty().bind(heightProperty());
        background.arcWidthProperty().bind(heightProperty());
        getChildren().addAll(background, box);
    }
}
