package dev.glycine.pracstation.components;

import javafx.scene.Group;
import javafx.scene.control.Label;
import lombok.Getter;

public class SignalGroup extends Group {
    @Getter
    Label label = new Label();

    @Getter
    String labelText;

    public void setLabelText(String labelText) {
        this.labelText = labelText;
        label.setText(labelText);
        label.getStyleClass().add("signal-label");
    }

    @Getter
    Double labelXOffset;

    public void setLabelXOffset(Double labelXOffset) {
        label.setLayoutX(labelXOffset);
    }

    @Getter
    Double labelYOffset;

    public void setLabelYOffset(Double labelYOffset) {
        label.setLayoutY(labelYOffset);
    }

    @Getter
    Double rotateZ;
    public void setRotateZ(Double rotateZ) {
        getChildren().forEach(child -> child.setRotate(rotateZ));
    }

    public SignalGroup() {
        getChildren().addAll(label);
    }
}
