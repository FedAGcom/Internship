package com.fedag.internship.service.impl;

import com.fedag.internship.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String FEDAG_USERNAME;

    @Override
    public void send(String to, String subject, String text) {
        log.info("Отправка сообщения пользователю: {}", to);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(FEDAG_USERNAME);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        mailSender.send(mailMessage);
        log.info("Сообщение пользователю: {} отправлено", to);
    }


    @Override
    @SneakyThrows
    public void sendHtml(String to, String subject, String text) {
        log.info("Отправка сообщения пользователю в виде html: {}", to);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setFrom(FEDAG_USERNAME);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);
        mailSender.send(mimeMessage);
        log.info("Сообщение пользователю в виде html: {} отправлено", to);
    }
}
