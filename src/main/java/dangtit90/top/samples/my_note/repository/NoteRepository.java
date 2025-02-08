package dangtit90.top.samples.my_note.repository;

import dangtit90.top.samples.my_note.domain.entity.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {
    public List<Note> findByOwner(String owner);
}
