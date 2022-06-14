package com.fedag.internship.register;

import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;

public interface EmailSenderService {
    void send(String to, String subject, String message);
}
