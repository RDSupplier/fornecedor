package ada.tech.fornecedor.controllers;

import ada.tech.fornecedor.domain.dto.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificacaoController {
    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping("/enviar-email")
    public String enviarEmail(
            @RequestBody EmailDto emailDto
    ) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(emailDto.getDestinatario());
        simpleMailMessage.setSubject(emailDto.getAssunto());
        simpleMailMessage.setText(emailDto.getMensagem());

        javaMailSender.send(simpleMailMessage);

        return "Email enviado com sucesso";
    }
}
