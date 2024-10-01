package summarybuddy.server.ai;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.longrunning.OperationFuture;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.speech.v1.*;
import java.io.FileInputStream;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import summarybuddy.server.ai.dto.request.ContentRequest;
import summarybuddy.server.ai.dto.request.Contents;
import summarybuddy.server.ai.dto.request.RequestParts;
import summarybuddy.server.ai.dto.request.SystemInstruction;
import summarybuddy.server.ai.dto.response.ContentResponse;
import summarybuddy.server.ai.dto.response.Parts;
import summarybuddy.server.common.exception.InternalServerException;
import summarybuddy.server.common.type.error.CommonErrorType;
import summarybuddy.server.common.type.error.ReportErrorType;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleClient {
    private final RestTemplate restTemplate;

    @Value("${google.ai.credential}")
    private String googleAiCridential;

    @Value("${google.ai.key}")
    private String googleAiKey;

    public String speechToText(String audioUrl) {

        try {
            CredentialsProvider credentialsProvider =
                    FixedCredentialsProvider.create(
                            ServiceAccountCredentials.fromStream(
                                    new FileInputStream(googleAiCridential)));
            SpeechSettings settings =
                    SpeechSettings.newBuilder().setCredentialsProvider(credentialsProvider).build();
            SpeechClient speechClient = SpeechClient.create(settings);

            RecognitionAudio recognitionAudio =
                    RecognitionAudio.newBuilder().setUri(audioUrl).build();

            // Builds the sync recognize request
            RecognitionConfig config =
                    RecognitionConfig.newBuilder()
                            .setEncoding(RecognitionConfig.AudioEncoding.MP3)
                            .setSampleRateHertz(44100)
                            .setLanguageCode("ko-KR")
                            .build();

            OperationFuture<LongRunningRecognizeResponse, LongRunningRecognizeMetadata>
                    recognizeResponse =
                            speechClient.longRunningRecognizeAsync(config, recognitionAudio);
            while (!recognizeResponse.isDone()) {
                System.out.println("Waiting for response...");
                Thread.sleep(10000);
            }
            List<SpeechRecognitionResult> results = recognizeResponse.get().getResultsList();

            StringBuilder resultBuilder = new StringBuilder();

            for (SpeechRecognitionResult result : results) {
                SpeechRecognitionAlternative alternative = result.getAlternativesList().getFirst();
                log.info("Transcription: {}", alternative.getTranscript());
                resultBuilder.append("\n").append(alternative.getTranscript());
            }
            return resultBuilder.toString();
        } catch (Exception e) {
            log.info("EXCEPTION: {}", e.getMessage());
            throw new InternalServerException(CommonErrorType.INTERNAL_SERVER);
        }
    }

    public String getSummary(String text, List<String> participants) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ContentRequest body =
                new ContentRequest(
                        new SystemInstruction(
                                new RequestParts(
                                        "당신은 최고의 회의 요약가입니다."
                                                + "제공되는 내용은 전체 회의의 녹음 텍스트입니다."
                                                + "회의 내용을 요약하여 회의록을 한국말로 작성하세요."
                                                + "회의록에는 '회의 주제', '참석자', '진행 시간', "
                                                + "'추정되는 분위기', '요약' 항목이 필수적으로 들어가야 하며"
                                                + "추가적인 특이사항이 있다면 작성하세요."
                                                + "회의의 참석자 닉네임은 "
                                                + participants
                                                + "입니다.")),
                        new Contents(new RequestParts(text)));

        HttpEntity<ContentRequest> request = new HttpEntity<>(body, headers);
        ContentResponse response =
                restTemplate.postForObject(
                        "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-pro-latest:generateContent"
                                + "?key="
                                + googleAiKey,
                        request,
                        ContentResponse.class);
        log.info("Result: {}", response);
        if (response != null && !response.candidates().isEmpty()) {
            List<Parts> parts = response.candidates().getFirst().content().parts();
            if (parts != null && !parts.isEmpty()) {
                return parts.getFirst().text();
            }
        }
        throw new InternalServerException(ReportErrorType.NO_SUMMARY_RESULT);
    }
}
