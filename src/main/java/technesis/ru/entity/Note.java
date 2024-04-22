package technesis.ru.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notes")
@Getter
@Setter
@ToString
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date dateOfCreation;
    private String header;
    @Column(length = 2000)
    private String content;

    public Note() {
        dateOfCreation = new Date();
    }

    public boolean isEmpty() {
        return header != null && !header.isEmpty() && content != null && !content.isEmpty();
    }
}
