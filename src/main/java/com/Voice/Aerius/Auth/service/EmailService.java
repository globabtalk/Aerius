package com.Voice.Aerius.Auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender mailSender;
    private PasswordResetTokenService passwordResetTokenService;
    private static String PASSWORD_RESET_LINK =  "https://Aerius/reset-password?token=%s";
    private static String ForgotPasswordTemplate = """
            Please reset your password here %s
            Your link will expire in an hour
            """;

    @Autowired
    public EmailService(PasswordResetTokenService passwordResetTokenService, JavaMailSender mailSender){
        this.passwordResetTokenService = passwordResetTokenService;
        this.mailSender = mailSender;

    }
    public void sendForgotPasswordMail(String emailTo){
        final String token = passwordResetTokenService.createOrRetrieveToken(emailTo);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setText(String.format(ForgotPasswordTemplate, String.format(PASSWORD_RESET_LINK, token)));
        simpleMailMessage.setTo(emailTo);
        mailSender.send(simpleMailMessage);
    }
}
