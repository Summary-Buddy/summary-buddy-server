package summarybuddy.server.storage;

import java.io.*;
import java.nio.file.Files;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import summarybuddy.server.common.exception.InternalServerException;
import summarybuddy.server.common.type.error.ReportErrorType;

@Slf4j
@Service
public class FFmpegClient {
    @Value("${output-dir-root}")
    private String outputDirRoot;

    public InputStream convertInputStreamToMp3(InputStream inputStream) {
        String inputDir = outputDirRoot + "input.webm";
        String outputDir = outputDirRoot + "output.mp3";
        File source = new File(inputDir);
        File target = new File(outputDir);
        try {
            Files.copy(inputStream, source.toPath());

            // FFmpeg 명령어
            String[] command = {"ffmpeg", "-i", inputDir, "-f", "mp3", outputDir};

            // ProcessBuilder로 프로세스 시작
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            // FFmpeg 프로세스 종료 대기
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                FileInputStream input = new FileInputStream(target);
                log.info("Conversion successful! Output file: " + outputDir);
                source.delete();
                target.delete();
                return input;
            } else {
                log.info("Conversion failed with exit code: " + exitCode + " " + outputDir);
            }
        } catch (Exception e) {
            log.info("EXCEPTION: {}", e.getMessage());
        }
        source.delete();
        target.delete();
        throw new InternalServerException(ReportErrorType.WAV_CONVERT);
    }
}
