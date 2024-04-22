package technesis.ru.controller;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import org.kordamp.ikonli.javafx.FontIcon;
import org.springframework.stereotype.Controller;

import java.util.Timer;
import java.util.TimerTask;

@Controller
@Slf4j
public class MessageController extends VBox {
    public MessageController() {
        this.setMaxWidth(USE_PREF_SIZE);
        this.setMaxHeight(USE_PREF_SIZE);
    }

    public void putMessage(Alert.AlertType type, String content) {
        Node node = createMessagePane(type, content);
        this.getChildren().add(node);
        log.info("message: " + content);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(() -> MessageController.this.getChildren().remove(node));
            }
        };
        timer.schedule(task, 5000L);
    }

    private Node createMessagePane(Alert.AlertType type, String content) {
        HBox pane = new HBox();
        pane.setMaxWidth(280);
        pane.setMinWidth(280);
        pane.setAlignment(Pos.CENTER);
        VBox.setMargin(pane, new Insets(10));

        FontIcon icon;
        if (type == Alert.AlertType.ERROR) {
            pane.setStyle("-fx-background-color: rgba(255,0,0,0.7)");
            icon = new FontIcon("gmi-error-outline");
        } else if (type == Alert.AlertType.CONFIRMATION) {
            pane.setStyle("-fx-background-color: rgba(0,255,0,0.7)");
            icon = new FontIcon("gmi-check-circle");
//            icon = new MaterialDesignIconView(MaterialDesignIcon.CHECK_CIRCLE_OUTLINE);
        } else {
            pane.setStyle("-fx-background-color: rgba(0,0,255,0.7)");
            icon = new FontIcon("gmi-error-outline");
        }
        icon.setIconSize(36);
        HBox.setMargin(icon, new Insets(10));
        pane.getChildren().add(icon);

        Label contentLabel = new Label(content);
        contentLabel.setWrapText(true);
        contentLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(contentLabel, Priority.ALWAYS);
        contentLabel.setPadding(new Insets(5));
        pane.getChildren().add(contentLabel);

        FontIcon closeIcon = new FontIcon("gmi-close");
        closeIcon.setOnMouseClicked(mouseEvent -> Platform.runLater(() -> MessageController.this.getChildren().remove(pane)));
        HBox.setMargin(closeIcon, new Insets(5));
        pane.getChildren().add(closeIcon);

        return pane;
    }
}
