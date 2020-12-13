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
        title.setText(value);
    }

    private final Label title = new Label();
    protected final Rectangle background = new Rectangle();

    public Card() {
        super();
        getChildren().addAll(background, title);

        background.getStyleClass().add("card-background");
        background.heightProperty().bind(heightProperty());
        background.widthProperty().bind(widthProperty());

        title.getStyleClass().add("card-title");
        setAlignment(title, Pos.TOP_LEFT);

        var fadein = new FadeTransition(Duration.millis(FADE_DURATION), title);
        fadein.setFromValue(0);
        fadein.setToValue(1);

        var fadeout = new FadeTransition(Duration.millis(FADE_DURATION), title);
        fadeout.setFromValue(1);
        fadeout.setToValue(0);

        setOnMouseEntered(e -> {
            title.setTextFill(AppleColor.GRAY);
            fadein.play();
        });

        setOnMouseExited(e -> fadeout.play());
    }
}
