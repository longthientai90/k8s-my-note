package dangtit90.top.samples.my_note.service;

import dangtit90.top.samples.my_note.config.AppProperties;
import dangtit90.top.samples.my_note.domain.entity.Note;
import dangtit90.top.samples.my_note.repository.NoteRepository;
import org.apache.logging.log4j.util.Strings;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class NoteService {
    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer renderer = HtmlRenderer.builder().build();
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private AppProperties properties;

    public void getAllNotes(Model model) {
        List<Note> notes = noteRepository.findAll();
        Collections.reverse(notes);
        model.addAttribute("notes", notes);
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
            if(Strings.isNotEmpty(attachment)) {
                note.setAttachment(attachment);
                note.setFileName(fileName);
            }
            noteRepository.save(note);

            // after publish you need to clean up the model
            model.addAttribute("description", "");
            model.addAttribute("fileName", "");
            model.addAttribute("attachment", "");
        }
    }
}
