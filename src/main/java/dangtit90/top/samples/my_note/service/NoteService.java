package dangtit90.top.samples.my_note.service;

import dangtit90.top.samples.my_note.config.AppProperties;
import dangtit90.top.samples.my_note.domain.entity.Note;
import dangtit90.top.samples.my_note.repository.NoteRepository;
import dangtit90.top.samples.my_note.web.response.NodeResponse;
import org.apache.logging.log4j.util.Strings;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NoteService {
    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer renderer = HtmlRenderer.builder().build();
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private AppProperties properties;

    public void getAllNotes(Model model) {
        List<Note> allNotes = noteRepository.findAll();
        model.addAttribute("notes", buildNodeResponseList(allNotes));
    }

    private List<NodeResponse> buildNodeResponseList(List<Note> notes) {
        TimeZone timeZone = TimeZone.getTimeZone(properties.getTimeZone());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(timeZone);
        List<NodeResponse> nodeResponses = new ArrayList<>();
        notes.forEach(note -> {
            NodeResponse item = new NodeResponse();
            BeanUtils.copyProperties(note, item);
            item.setCreatedDate(formatter.format(note.getCreatedDate()));
            nodeResponses.add(item);
        });
        return nodeResponses;
    }

    public void uploadImage(MultipartFile file, String description, Model model) throws Exception {
        File uploadsDir = new File(properties.getUploadDir());
        if (!uploadsDir.exists()) {
            uploadsDir.mkdirs();
        }
        String fileId = UUID.randomUUID() + "." +
                file.getOriginalFilename().split("\\.")[1];
        file.transferTo(new File(properties.getUploadDir() + fileId));

        // clean up the model
        model.addAttribute("description", description);
        model.addAttribute("fileName", file.getOriginalFilename());
        model.addAttribute("attachment", fileId);
    }

    public void saveNote(String description, String fileName, String attachment, Model model) {
        if (description != null && !description.trim().isEmpty()) {
            // translate markup to HTML
            Node document = parser.parse(description.trim());
            String html = renderer.render(document);

            // save note
            Note note = new Note();
            note.setDescription(html);
            if (Strings.isNotEmpty(attachment)) {
                note.setAttachment(attachment);
                note.setFileName(fileName);
            }
            note.setCreatedDate(new Date());
            noteRepository.save(note);

            // after publish you need to clean up the model
            model.addAttribute("description", "");
            model.addAttribute("fileName", "");
            model.addAttribute("attachment", "");
        }
    }
}
