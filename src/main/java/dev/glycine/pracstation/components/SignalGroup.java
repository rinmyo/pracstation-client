package dev.glycine.pracstation.components;

import dev.glycine.pracstation.Direction;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import lombok.Getter;

public class SignalGroup extends Group {
    private static final Double DEFAULT_FONT_SIZE = 7.0;
    private static final Paint DEFAULT_TEXT_FILL = AppleColor.WHITE;

    @Getter
    Label label = new Label();

    @Getter
    String labelText;

    public void setLabelText(String labelText) {
        this.labelText = labelText;
        label.setText(labelText);
        label.setTextFill(DEFAULT_TEXT_FILL);
        label.setFont(Font.font(DEFAULT_FONT_SIZE));
    }

    @Getter
    Double labelOffset;
    public void setLabelOffset(Double labelOffset) {
        label.setLayoutX(labelOffset);
    }

    public SignalGroup() {
        System.out.println(0);
        getChildren().addAll(label);
    }
}
