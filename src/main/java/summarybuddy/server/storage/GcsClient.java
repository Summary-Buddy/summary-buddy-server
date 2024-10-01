package summarybuddy.server.storage;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import summarybuddy.server.common.exception.InternalServerException;
import summarybuddy.server.common.type.error.CommonErrorType;

@Slf4j
@Component
public class GcsClient {
    @Value("${google.ai.credential}")
    private String googleAiCridential;

    @Value("${google.storage.bucket-name}")
    private String bucketName;

    private final FFmpegClient ffmpegClient;

    public GcsClient(FFmpegClient ffmpegClient) {
        this.ffmpegClient = ffmpegClient;
    }

    public String createAudioUrl(InputStream input) {
        try {
            Storage storage = getStorage();
            String objectName = "record/new-file-" + LocalDateTime.now().getNano() + ".mp3";
            uploadObject(input, objectName, "audio/mp3", storage);

            return "gs://" + bucketName + "/" + objectName;
        } catch (Exception e) {
            log.info("EXCEPTION: {}", e.getMessage());
            throw new InternalServerException(CommonErrorType.INTERNAL_SERVER);
        }
    }

    public String createPdfDirectory(InputStream input) {
        try {
            Storage storage = getStorage();
            String objectName = "pdf/new-file-" + LocalDateTime.now().getNano();
            uploadObject(input, objectName, "application/pdf", storage);

            return objectName;
        } catch (Exception e) {
            log.info("EXCEPTION: {}", e.getMessage());
            throw new InternalServerException(CommonErrorType.INTERNAL_SERVER);
        }
    }

    private Storage getStorage() throws IOException {
        CredentialsProvider credentialsProvider =
                FixedCredentialsProvider.create(
                        ServiceAccountCredentials.fromStream(
                                new FileInputStream(googleAiCridential)));
        Storage storage =
                StorageOptions.newBuilder()
                        .setCredentials(credentialsProvider.getCredentials())
                        .build()
                        .getService();
        return storage;
    }

    private void uploadObject(
            InputStream input, String objectName, String contentType, Storage storage)
            throws IOException {
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();
        Storage.BlobWriteOption precondition = Storage.BlobWriteOption.doesNotExist();
        storage.createFrom(blobInfo, input, precondition);
    }

    public String getUrl(String fileDirectory) {
        return "https://storage.cloud.google.com/"
                + bucketName
                + "/"
                + fileDirectory
                + "?authuser=3";
    }
}
