package dangtit90.top.samples.my_note.web.response;

import lombok.Data;

@Data
public class NodeResponse {
    private String id;
    private String description;
    private String fileName;
    private String attachment;
    private String createdDate;
}
