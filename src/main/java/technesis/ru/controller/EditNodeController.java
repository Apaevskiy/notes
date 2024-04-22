package technesis.ru.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;
import technesis.ru.config.CustomPane;
import technesis.ru.entity.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

@Controller
public class EditNodeController extends CustomPane {

    @FXML
    private Label titleField;
    @FXML
    private TextField headerTextField;
    @FXML
    private Label dateOfCreationLabel;
    @FXML
    private TextArea contentTextArea;
    @FXML
    private Button confirmButton;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public EditNodeController() {
        super("/fxml/edit_note.fxml");
    }

    @Override
    public void initialize() {
        this.setVisible(false);


    }

    public void closeModal() {
        this.setVisible(false);
        clearFields();
    }

    public Note parseField() {
        Note note = new Note();
        note.setHeader(headerTextField.getText());
        note.setContent(contentTextArea.getText());
        return note;
    }

    @FXML
    private void cancelAction(ActionEvent event) {
        closeModal();
    }

    public EditNodeController title(String title) {
        titleField.setText(title);
        return this;
    }

    public EditNodeController confirmButton(String buttonText, Consumer<EditNodeController> o) {
        confirmButton.setText(buttonText);
        confirmButton.setOnAction(actionEvent -> o.accept(EditNodeController.this));
        return this;
    }

    public void clearFields() {
        headerTextField.setText(null);
        contentTextArea.setText(null);
        dateOfCreationLabel.setText(null);
    }

    public void show() {
        EditNodeController.this.setVisible(true);
    }

    public void disableField(boolean b) {
        headerTextField.setDisable(b);
        contentTextArea.setDisable(b);
    }

    public void setNote(Note note) {
        headerTextField.setText(note.getHeader());
        contentTextArea.setText(note.getContent());
        dateOfCreationLabel.setText("Дата создания заявки: " + dateFormat.format(note.getDateOfCreation()));
    }

    public String formatDate(Date date) {
        return dateFormat.format(date);
    }
}
