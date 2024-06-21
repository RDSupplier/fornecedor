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
                <html>
                <body style="font-family: 'rdmodern-medium', sans-serif; width: 100%%;">
                    <div style="text-align: center;">
                        <h2 style="color: #0F8143;">Redefinição de senha</h2>
                        <p>Olá,</p>
                        <br>
                        <p>Você solicitou sua redefinição de senha</p>
                        <p>Seu código de confirmação é: <span style="font-weight: bold;">%s</span></p>
                        <br>
                        <p>Se você não solicitou esta redefinição, por favor ignore este email.</p>
                        <p>Atenciosamente,<br/>RDSupplier</p>
                    </div>
                </body>
                </html>
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
