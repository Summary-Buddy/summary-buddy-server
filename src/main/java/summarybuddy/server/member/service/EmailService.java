package summarybuddy.server.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendTemporaryPasswordEmail(String email, String tempPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("임시 비밀번호 발송");
        message.setText("안녕하세요 summay buddy 입니다, \n\n임시 비밀번호는 다음과 같습니다: " + tempPassword + "\n\n로그인 후 비밀번호를 변경해 주세요.");
        mailSender.send(message);
    }
}