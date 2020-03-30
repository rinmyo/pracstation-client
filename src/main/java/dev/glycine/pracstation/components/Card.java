package dev.glycine.pracstation.components;

import com.jfoenix.controls.JFXBadge;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.Getter;

/**
 * 卡片
 */
public class Card extends JFXBadge {
    private static final int FADE_DURATION = 150;

    @Getter
    private String labelText;

    public void setLabelText(String value) {
        titleLabel.setText(value);
    }

    Rectangle bgRect = new Rectangle();
    JFXBadge recBox = new JFXBadge(bgRect);
    Label titleLabel = new Label();

    public Card() {
        super();
        bgRect.getStyleClass().add("card-background");
        bgRect.heightProperty().bind(recBox.heightProperty());
        bgRect.widthProperty().bind(recBox.widthProperty());

        titleLabel.getStyleClass().add("card-title");
        setAlignment(titleLabel, Pos.TOP_LEFT);

        FadeTransition fadein = new FadeTransition(Duration.millis(FADE_DURATION), titleLabel);
        fadein.setFromValue(0);
        fadein.setToValue(1);

        FadeTransition fadeout = new FadeTransition(Duration.millis(FADE_DURATION), titleLabel);
        fadeout.setFromValue(1);
        fadeout.setToValue(0);

        setOnMouseEntered(e -> {
            titleLabel.setTextFill(AppleColor.GRAY);
            fadein.play();
        });

        setOnMouseExited(e -> fadeout.play());

        getChildren().addAll(recBox, titleLabel);
    }

}
