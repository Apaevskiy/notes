package technesis.ru.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import technesis.ru.entity.Note;
import technesis.ru.repository.NoteRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public void deleteNote(Note note) {
        noteRepository.delete(note);
    }

    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }

    public List<Note> getNotes() {
        return noteRepository.findAll();
    }
}
