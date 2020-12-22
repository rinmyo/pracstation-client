package dev.glycine.pracstation.models;

import javafx.scene.shape.Line;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * 軌道區段（軌道電路）
 */
@Log4j2
public class Section extends Line {
    @Getter
    private static final HashMap<String, ArrayList<Section>> sections = new HashMap<>();

    public static void register(Section section) {
        ArrayList<Section> list;
        if (sections.containsKey(section.sid)) {
            list = sections.get(section.sid);
        } else {
            list = new ArrayList<>();
            sections.put(section.sid, list);
        }
        list.add(section);
        log.debug("register section: " + section.sid);
    }

    public static ArrayList<Section> getSectionsBySid(String sid) {
        return sections.get(sid);
    }

    @Getter
    String sid;

    public void setSid(String sid) {
        this.sid = sid;
        register(this);
    }

    SectionStateProperty stateProperty = new SectionStateProperty();

    public void setState(SectionState state) {
        stateProperty.set(state);
    }

    public SectionState getState() {
        return stateProperty.get();
    }

    @Getter
    String dep;
    @Getter
    HashMap<String, TurnoutState> dependencies = new HashMap<>();

    public void setDep(String dep) {
        Arrays.stream(dep.trim().split(","))
                .map(s -> s.trim().split("-"))
                .collect(Collectors.toList())
                .forEach(s -> {
                    dependencies.put(s[0], TurnoutState.valueOf(s[1]));
                });
    }

    public Section() {
        setStrokeWidth(1.5);
        //setStrokeLineCap(StrokeLineCap.ROUND);
        getStyleClass().add("-railway-section");
        stateProperty.addListener((observable, oldvalue, newvalue) -> setStroke(newvalue.getColor()));
    }
}
