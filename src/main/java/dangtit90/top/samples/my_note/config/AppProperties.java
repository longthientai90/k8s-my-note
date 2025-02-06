package dangtit90.top.samples.my_note.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AppProperties {
    @Value("${my-note.upload-dir}")
    private String uploadDir;

    @Value("${my-note.time-zone}")
    private String timeZone;
}