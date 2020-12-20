package dev.glycine.pracstation.components;

import dev.glycine.pracstation.App;
import dev.glycine.pracstation.models.TurnoutState;
import javafx.fxml.FXML;
import javafx.scene.shape.Line;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;


/**
 * 軌道區段（軌道電路）
 */
@Log4j2
public class SectionComponent extends Line {
    @Getter
    @Setter
    String sid;

    @Getter
    @Setter
    String tid;

    @Getter
    @Setter
    TurnoutState tstate;

    public SectionComponent() {
        setStrokeWidth(1.5);
        //setStrokeLineCap(StrokeLineCap.ROUND);
        getStyleClass().add("-railway-section");
        setOnMouseClicked(e -> {
            var dx = getEndX() - getStartX();
            var dy = getEndY() - getStartY();
            var newX = getStartX() + (dx) / 2;
            var newY = getStartY() + (dy) / 2;
            log.info(sid + " " + getEndX() + " " + getEndY() + " " + getStartX() + " " + getStartY() + " -> " + newX + " " + newY);
            if (getStroke() == AppleColor.WHITE) {
                setStroke(AppleColor.TEAL);
            } else {
                setStroke(AppleColor.WHITE);
            }
        });
    }
}
