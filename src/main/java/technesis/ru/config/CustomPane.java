package technesis.ru.config;


import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;

@Slf4j
public abstract class CustomPane extends StackPane {
    private final String path;

    public CustomPane(String path) {
        this.path = path;
    }

    public abstract void initialize();

    public void load(Pane parent) {
        this.load();
        parent.getChildren().add(this);
    }

    public void load() {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = getClass().getResource(path);
            loader.setLocation(xmlUrl);
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
            this.setId(this.getClass().getName());

        } catch (IOException exception) {
            log.error("Ошибка загрузки fxml (" + path + "): " + exception.getMessage());
        }
    }


}
