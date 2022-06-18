package com.fedag.internship.service;

public interface EmailSenderService {
    void send(String to, String subject, String message);
}
