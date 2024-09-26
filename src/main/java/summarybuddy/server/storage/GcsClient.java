package summarybuddy.server.storage;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class GcsClient {
    @Value("${google.ai.credential}")
    private String googleAiCridential;

    @Value("${google.storage.bucket-name}")
    private String bucketName;

    public String createAudioUrl(MultipartFile file) {
        try {
            CredentialsProvider credentialsProvider =
                    FixedCredentialsProvider.create(
                            ServiceAccountCredentials.fromStream(
                                    new FileInputStream(googleAiCridential)));
            Storage storage =
                    StorageOptions.newBuilder()
                            .setCredentials(credentialsProvider.getCredentials())
                            .build()
                            .getService();
            String objectName = "record/new-file-" + LocalDateTime.now().getNano();
            BlobId blobId = BlobId.of(bucketName, objectName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            Storage.BlobWriteOption precondition = Storage.BlobWriteOption.doesNotExist();
            storage.createFrom(blobInfo, file.getInputStream(), precondition);

            return "gs://" + bucketName + "/" + objectName;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
