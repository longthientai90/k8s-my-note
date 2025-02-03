package dangtit90.top.samples.my_note.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "my-note")
@Data
public class AppProperties {
    private String uploadDir;
}