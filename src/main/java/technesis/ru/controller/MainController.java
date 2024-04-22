package technesis.ru.controller;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import org.springframework.stereotype.Controller;

@Controller
public class MainController extends StackPane {
    private final EditNodeController editNodeController;
    private final NoteController noteController;
    private final MessageController messageController;

    public MainController(EditNodeController editNodeController, NoteController noteController, MessageController messageController) {
        this.editNodeController = editNodeController;
        this.noteController = noteController;
        this.messageController = messageController;
        initialize();
    }

    public void initialize() {
        this.setPrefWidth(800);
        this.setPrefHeight(500);

        this.getStylesheets().add("/css/main.css");
        this.getStylesheets().add("/css/dialog.css");

        noteController.load(this);
        editNodeController.load(this);

        this.getChildren().add(messageController);
        StackPane.setAlignment(messageController, Pos.BOTTOM_RIGHT);
        messageController.toFront();
    }
}
