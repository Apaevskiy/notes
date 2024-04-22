package technesis.ru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import technesis.ru.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
