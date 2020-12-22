package dev.glycine.pracstation.models;

import javafx.scene.shape.Line;

public class IsulatedJoint extends Line {
    public IsulatedJoint() {
        setStartY(-2);
        setEndY(2);
        setStrokeWidth(0.75);
        getStyleClass().add("-isulated-rail-joint");
    }
}
