package dev.glycine.pracstation.components;

import dev.glycine.pracstation.Direction;
import dev.glycine.pracstation.models.Turnout;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

/**
 * 道岔表示面板
 */
public class TurnoutPane extends VBox {
    @Getter
    private Direction dir;

    private HBox firstLayer = new HBox();
    private HBox secondLayer = new HBox();

    public void setDir(Direction dir) {
        Turnout.getTurnouts().forEach((k, v) -> {
            switch (dir) {
                case S:
                    if (k % 2 == 0)
                        if (k < 0.5 * Turnout.getTurnouts().size()) {
                            firstLayer.getChildren().add(v.getIndicator());
                        } else {
                            secondLayer.getChildren().add(v.getIndicator());
                        }
                    break;

                case X:
                    if (k % 2 != 0)
                        if (k < 0.5 * Turnout.getTurnouts().size()) {
                            firstLayer.getChildren().add(v.getIndicator());
                        } else {
                            secondLayer.getChildren().add(v.getIndicator());
                        }
                    break;
            }
        });
        this.dir = dir;
    }

    public TurnoutPane() {
        getChildren().addAll(firstLayer, secondLayer);
    }
}
