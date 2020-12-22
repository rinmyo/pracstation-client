package dev.glycine.pracstation.models;

import dev.glycine.pracstation.exceptions.UnknownSectionStateException;
import dev.glycine.pracstation.exceptions.UnknownSignalStateException;
import dev.glycine.pracstation.pb.Section;
import dev.glycine.pracstation.pb.Signal;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SectionState {

    LOCKED(AppleColor.WHITE),

    UNKNOWN(AppleColor.GRAY),

    BROKEN(AppleColor.GRAY),

    OCCUPIED(AppleColor.RED),

    FREE(AppleColor.TEAL);

    /**
     * 燈光顏色
     */
    @Getter
    private final Color color;

    public static SectionState fromPbSectionState(Section.SectionState pbSignalState) throws UnknownSectionStateException {
        return switch(pbSignalState){
            case UNKNOWN -> UNKNOWN;
            case BROKEN -> BROKEN;
            case OCCUPIED -> OCCUPIED;
            case FREE -> FREE;
            case LOCKED -> LOCKED;
            case UNRECOGNIZED -> throw new UnknownSectionStateException();
        };
    }
}
