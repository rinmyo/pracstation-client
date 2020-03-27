package dev.glycine.pracstation.gui;

import com.jfoenix.controls.JFXBadge;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.Getter;


public class Card extends JFXBadge {
    @Getter
    private String labelText;
    Rectangle bgRect = new Rectangle();

    JFXBadge recBox = new JFXBadge(bgRect);
    Label titleLabel = new Label();

    public void setLabelText(String value) {
        titleLabel.setText(value);
    }

    public Card() {
        super();
        bgRect.getStyleClass().add("card-background");
        bgRect.heightProperty().bind(recBox.heightProperty());
        bgRect.widthProperty().bind(recBox.widthProperty());

        titleLabel.getStyleClass().add("card-title");
        setAlignment(titleLabel, Pos.TOP_LEFT);

        FadeTransition fadein = new FadeTransition(Duration.millis(150), titleLabel);
        fadein.setFromValue(0);
        fadein.setToValue(1);

        FadeTransition fadeout = new FadeTransition(Duration.millis(150), titleLabel);
        fadeout.setFromValue(1);
        fadeout.setToValue(0);

        setOnMouseEntered(e -> {
            titleLabel.setTextFill(Color.rgb(142, 142, 147));
            fadein.play();
        });

        setOnMouseExited(e -> {
            fadeout.play();
        });

        getChildren().addAll(recBox, titleLabel);
    }

}
