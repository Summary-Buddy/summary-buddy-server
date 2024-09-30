package summarybuddy.server.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import summarybuddy.server.common.exception.InternalServerException;
import summarybuddy.server.common.type.error.ReportErrorType;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

@Component
public class Jave2Client {
    @Value("${output-dir-root}")
    private String outputDirRoot;

    public InputStream convertToMp3(InputStream input) {
        File source = new File(outputDirRoot + "input.webm");
        File target = new File(outputDirRoot + "output.mp3");
        try {
            Files.copy(input, source.toPath());

            // Audio Attributes
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libmp3lame"); // Change this to flac if you prefer flac
            audio.setBitRate(128000);
            audio.setChannels(2);
            audio.setSamplingRate(44100);

            // Encoding attributes
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setOutputFormat("mp3"); // Change to flac if you prefer flac
            attrs.setAudioAttributes(audio);

            // Encode
            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(source), target, attrs);
            FileInputStream resultInputStream = new FileInputStream(target);
            source.delete();
            target.delete();

            return resultInputStream;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        source.delete();
        target.delete();
        throw new InternalServerException(ReportErrorType.MP3_CONVERT);
    }
}
