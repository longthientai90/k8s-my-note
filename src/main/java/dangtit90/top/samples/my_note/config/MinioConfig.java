package dangtit90.top.samples.my_note.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("k8s")
@Slf4j
public class MinioConfig {
    @Autowired
    private MinioProperties properties;

    @Bean
    public MinioClient minioClient() throws InterruptedException {
        boolean success = false;
        int counter = 0;
        while (!success && counter < 5) {
            try {
                MinioClient minioClient = MinioClient.builder()
                        .credentials(properties.getAccessKey(), properties.getSecretKey())
                        .endpoint(properties.getHost(), properties.getPort(), properties.isUseSSL())
                        .build();

                // Check if the bucket already exists.
                BucketExistsArgs existsArgs = BucketExistsArgs.builder().bucket(properties.getBucket()).build();
                boolean isExist = minioClient.bucketExists(existsArgs);
                if (isExist) {
                    log.info("Bucket already exists: {}", properties.getBucket());
                } else {
                    MakeBucketArgs bucketArgs = MakeBucketArgs.builder().bucket(properties.getBucket()).build();
                    minioClient.makeBucket(bucketArgs);
                }
                success = true;
                return minioClient;
            } catch (Exception e) {
                e.printStackTrace();
                log.warn("Minio Reconnect: {}", properties.isReconnectEnabled());
                if (properties.isReconnectEnabled()) {
                    Thread.sleep(2000);
                } else {
                    success = true;
                }
            }
            counter++;
        }
        log.info("Minio initialized!");
        return MinioClient.builder().build();
    }
}
