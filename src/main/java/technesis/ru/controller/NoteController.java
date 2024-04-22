package technesis.ru.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Controller;
import technesis.ru.config.CustomPane;
import technesis.ru.entity.Note;
import technesis.ru.service.NoteService;


@Controller
public class NoteController extends CustomPane {

    @FXML
    private ListView<Note> notesListView;
    @FXML
    private TextField headerTextField;
    @FXML
    private Label dateOfCreationLabel;
    @FXML
    private TextArea contentTextArea;


    private final EditNodeController editNoteController;
    private final NoteService noteService;
    private final MessageController messageController;

    public NoteController(EditNodeController editNoteController, NoteService noteService, MessageController messageController) {
        super("/fxml/note.fxml");
        this.editNoteController = editNoteController;
        this.noteService = noteService;
        this.messageController = messageController;
    }

    @Override
    public void initialize() {
        notesListView.setItems(FXCollections.observableArrayList(noteService.getNotes()));

        notesListView.setCellFactory(param -> {
            ListCell<Note> cell = new ListCell<Note>() {
                @Override
                protected void updateItem(Note item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getHeader());
                    }
                }
            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    fillFields(cell.getItem());
                    e.consume();
                }
            });
            return cell;
        });

    }

    @FXML
    private void addNoteAction(ActionEvent event) {
        editNoteController.disableField(false);
        editNoteController
                .title("Создание заметки")
                .confirmButton("Создать", modal -> {
                    Note editedNote = editNoteController.parseField();
                    if (editedNote != null && !editedNote.isEmpty()) {
                        messageController.putMessage(Alert.AlertType.ERROR, "Заполните все поля для создания заметки");
                        return;
                    }
                    try {
                        notesListView.getItems().add(noteService.saveNote(editedNote));
                        modal.closeModal();
                        messageController.putMessage(Alert.AlertType.CONFIRMATION, "Заметка успешно создана");
                    } catch (Exception e) {
                        messageController.putMessage(Alert.AlertType.ERROR, "Ошибка создания заметки\n" + e.getLocalizedMessage());
                    }
                })
                .show();
    }

    @FXML
    private void deleteNoteAction(ActionEvent event) {
        Note selectedNote = getSelectedItemOfListView();
        if (selectedNote == null) {
            messageController.putMessage(Alert.AlertType.ERROR, "Выберите заметку из списка для её удаления!");
            return;
        }

        editNoteController.disableField(true);
        editNoteController.setNote(selectedNote);
        editNoteController
                .title("Подтвердите удаление заметки")
                .confirmButton("Удалить заметку", modal -> {
                    try {
                        noteService.deleteNote(selectedNote);
                        notesListView.getItems().remove(selectedNote);
                        notesListView.refresh();
                        messageController.putMessage(Alert.AlertType.CONFIRMATION, "Заметка удалена");
                    } catch (Exception e) {
                        messageController.putMessage(Alert.AlertType.ERROR, "Ошибка удаления заметки\n" + e.getLocalizedMessage());
                    }
                    modal.closeModal();
                })
                .show();
    }

    @FXML
    private void editNoteAction(ActionEvent event) {
        Note selectedNote = getSelectedItemOfListView();
        if (selectedNote == null) {
            messageController.putMessage(Alert.AlertType.ERROR, "Выберите заметку из списка для её редактирования!");
            return;
        }
        editNoteController.disableField(false);
        editNoteController.setNote(selectedNote);
        editNoteController
                .title("Редактирование заметки")
                .confirmButton("Сохранить", modal -> {
                    try {
                        Note editedNote = editNoteController.parseField();
                        if (editedNote != null && !editedNote.isEmpty()) {
                            messageController.putMessage(Alert.AlertType.ERROR, "Заполните все поля для создания заметки");
                            return;
                        }
                        selectedNote.setHeader(editedNote.getHeader());
                        selectedNote.setContent(editedNote.getContent());

                        noteService.saveNote(selectedNote);
                        notesListView.refresh();
                        notesListView.getSelectionModel().clearSelection();
                        modal.closeModal();
                        messageController.putMessage(Alert.AlertType.CONFIRMATION, "Заметка успешно изменена");
                    } catch (Exception e) {
                        messageController.putMessage(Alert.AlertType.ERROR, "Ошибка сохранения заметки\n" + e.getLocalizedMessage());
                    }
                })
                .show();
    }

    private void fillFields(Note note) {
        headerTextField.setText(note.getHeader());
        dateOfCreationLabel.setText("Дата создания заметки: " + editNoteController.formatDate(note.getDateOfCreation()));
        contentTextArea.setText(note.getContent());
    }

    private Note getSelectedItemOfListView() {
        MultipleSelectionModel<Note> model = notesListView.getSelectionModel();
        if (model == null) {
            return null;
        }
        return model.getSelectedItem();
    }

}