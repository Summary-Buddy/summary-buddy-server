package summarybuddy.server.storage;

import java.io.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import summarybuddy.server.common.exception.InternalServerException;
import summarybuddy.server.common.type.error.ReportErrorType;

@Slf4j
@Service
public class FFmpegClient {
    @Value("${ffmpeg.dir}")
    private String outputDir;

    public InputStream convertInputStreamToWav(InputStream inputStream) {
        try {
            // FFmpeg 명령어
            String[] command = {
                "ffmpeg", "-i", "pipe:0", // stdin에서 입력받음
                outputDir
            };

            // ProcessBuilder로 프로세스 시작
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            // 입력 스트림을 FFmpeg 프로세스의 OutputStream에 연결
            OutputStream processInput = process.getOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                processInput.write(buffer, 0, bytesRead);
            }
            processInput.flush();
            processInput.close(); // 입력 스트림이 끝났음을 알림

            // 프로세스의 출력 읽기
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // FFmpeg 프로세스 종료 대기
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                FileInputStream input = new FileInputStream(outputDir);
                File file = new File(outputDir);
                file.delete();
                log.info("Conversion successful! Output file: " + outputDir);
                return input;
            } else {
                log.info("Conversion failed with exit code: " + exitCode + "\n" + output);
            }
        } catch (Exception e) {
            log.info("EXCEPTION: {}", e.getMessage());
        }
        throw new InternalServerException(ReportErrorType.WAV_CONVERT);
    }
}
