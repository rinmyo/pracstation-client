package dev.glycine.pracstation;

import javafx.application.Application;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AppLauncher {
    public static void main(String[] args) {
        Application.launch(App.class, args);
        log.info("Application has been launched.");
    }
}
