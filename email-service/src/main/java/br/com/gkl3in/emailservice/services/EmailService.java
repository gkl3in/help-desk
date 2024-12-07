package br.com.gkl3in.emailservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.dtos.OrderCreatedMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${mail.text-created-order-confirmation}")
    private String textCreatedOrderConfirmation;

    public void sendMail(
            final OrderCreatedMessage orderDTO
    ) {
        log.info("Enviando email para {}", orderDTO.getCustomer().email());

        SimpleMailMessage message = getSimpleMailMessage(orderDTO);
        try {
            mailSender.send(message);
        } catch (MailException e) {
            switch (e.getClass().getSimpleName()) {
                case "MailSendException":
                    log.error("Erro ao enviar email", e);
                    break;
                case "MailAuthenticationException":
                    log.error("Erro ao autenticar email", e);
                    break;
                case "MailPreparationException":
                    log.error("Erro ao preparar email", e);
                    break;
                case "MailParseException":
                    log.error("Erro durante o processo de análise do e-mail", e);
                    break;
                default:
                    log.error("Erro ao enviar email", e);
            }
        }
    }

    private SimpleMailMessage getSimpleMailMessage(OrderCreatedMessage orderDTO) {
        String subject = "Ordem de serviço criada";
        String text = String.format(textCreatedOrderConfirmation,
                orderDTO.getCustomer().name(),
                orderDTO.getOrder().id(),
                orderDTO.getOrder().title(),
                orderDTO.getOrder().description(),
                orderDTO.getOrder().createdAt(),
                orderDTO.getOrder().status(),
                orderDTO.getRequester().name());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setTo("gabrielklein289@hotmail.com");
        message.setText(text);
        return message;
    }
}