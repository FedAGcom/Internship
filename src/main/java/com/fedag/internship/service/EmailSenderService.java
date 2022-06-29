package com.fedag.internship.service;

public interface EmailSenderService {
    void send(String to, String subject, String message);

    void sendHtml(String to, String subject, String text);
}
