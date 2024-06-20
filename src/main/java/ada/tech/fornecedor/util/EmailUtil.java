package ada.tech.fornecedor.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage mineMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mineMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Código de confirmação");
        mimeMessageHelper.setText("""
                Olá, seu código de confirmação é: %s
                """.formatted(otp), true);


        javaMailSender.send(mineMessage);
    }

    public void sendPasswordEmail(String email) throws MessagingException {
        MimeMessage mineMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mineMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Redefinição de Senha");
        mimeMessageHelper.setText("""
                <div>
                    <a href="http://localhost:8080/redefinir-senha?email=%s" target="_blank">Redefinir Senha</a>
                </div>
                """.formatted(email), true);

        javaMailSender.send(mineMessage);
    }
}
