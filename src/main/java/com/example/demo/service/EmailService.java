package com.example.demo.service;

import com.example.demo.mail.Mail;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {
    public void sendSimpleMessage(Mail mail) throws MessagingException, IOException;
}
