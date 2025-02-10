package dangtit90.top.samples.my_note.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "notes")
@Data
public class Note {
    @Id
    private String id;
    private String description;
    private String fileName;
    private String attachment;
    private Date createdDate;

    @Indexed
    private String owner;

    @Override
    public String toString() {
        return description;
    }
}
