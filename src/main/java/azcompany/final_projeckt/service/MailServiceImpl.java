package azcompany.final_projeckt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    @Value("{spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendEmail(String to, String subject, String text) {
        log.info("ActionLog.sendEmail.start: to={}", to);
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            log.info("ActionLog.sendEmail.success: to={}", to);
        }catch (Exception e) {
            log.error("ActionLog.sendEmail.error: to={}, error={}", to, e.getMessage());
        }

    }
}
