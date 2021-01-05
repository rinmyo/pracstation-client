package dev.glycine.pracstation;

import javafx.application.Application;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.cli.*;

@Log4j2
public class AppLauncher {
    @Getter
    private static String fxmlPath;
    @Getter
    private static int windowWidth;
    @Getter
    private static int windowHeight;
    @Getter
    private static String host;
    @Getter
    private static int port;

    public static void main(String[] args) {
        var options = new Options()
                .addOption("fxml", true, "station fxml file path")
                .addOption("width", true, "window width")
                .addOption("height", true, "window height")
                .addOption("host", true, "server url")
                .addOption("port", true, "server port");

        var cliParser = new DefaultParser();
        var helpFormatter = new HelpFormatter();
        try {
            var cli = cliParser.parse(options, args);
            fxmlPath = cli.getOptionValue("fxml", "default.fxml");
            windowWidth = Integer.parseInt(cli.getOptionValue("width", "1920"));
            windowHeight = Integer.parseInt(cli.getOptionValue("height", "1080"));
            host = cli.getOptionValue("host", "127.0.0.1");
            port = Integer.parseInt(cli.getOptionValue("port", "8080"));
            log.info("launch station: " + fxmlPath + "  window size: " + windowWidth + "x" + windowHeight + "  host: " + host + ":" + port);
        } catch (ParseException e) {
            // 解析失败是用 HelpFormatter 打印 帮助信息
            helpFormatter.printHelp("控顯機使用方法", options);
            log.fatal(e);
            return;
        }
        Application.launch(App.class, args);
    }
}
