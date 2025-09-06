package com.Voice.Aerius.Auth.impl;


import lombok.Value;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@Service
public class EmailServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async
    public CompletableFuture<Boolean> sendHtmlEmail(String to, String subject, String templateName, Context context) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);

            String htmlContent = templateEngine.process(templateName, context);
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
            logger.info("Email sent successfully to: {}", to);
            return CompletableFuture.completedFuture(true);

        } catch (MessagingException e) {
            logger.error("Failed to send email to: {}", to, e);
            return CompletableFuture.completedFuture(false);
        }
    }

    @Async
    public CompletableFuture<Boolean> sendVerificationEmail(String to, String username, String verificationLink) {
        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("verificationLink", verificationLink);

        return sendHtmlEmail(to, "Verify Your Aerius Account", "verification-email", context);
    }
}
