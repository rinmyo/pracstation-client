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

    @Getter
    private final String routeId;
    private final Rectangle background = new Rectangle();


    public RouteBadge(String text) {
        routeId = text;
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

        var hbox = new HBox(startLabel, arrow, endLabel, warnIcon);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(2.5, 5, 2.5, 5));

        var fillred = new FillTransition(Duration.millis(FADE_DURATION), background);
        fillred.setFromValue(AppleColor.GRAY);
        fillred.setToValue(AppleColor.RED);

        var fillgray = new FillTransition(Duration.millis(FADE_DURATION), background);
        fillgray.setFromValue(AppleColor.RED);
        fillgray.setToValue(AppleColor.GRAY);

        setOnMouseEntered(e -> fillred.play());
        setOnMouseExited(e -> fillgray.play());
        setOnMouseClicked(MainController.getInstance()::handleCancelRoute);

        background.arcHeightProperty().bind(heightProperty());
        background.arcWidthProperty().bind(heightProperty());
        getChildren().addAll(background, hbox);
    }
}
