package dangtit90.top.samples.my_note.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("k8s")
@Getter
public class MinioProperties {
    @Value("${minio.host}")
    private String host;

    @Value("${minio.port}")
    private int port;

    @Value("${minio.bucket}")
    private String bucket;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Value("${minio.use-ssl}")
    private boolean useSSL;

    @Value("${minio.reconnect-enabled}")
    private boolean reconnectEnabled;
}
