package com.Voice.Aerius.massages.service;

import com.Voice.Aerius.massages.dto.EmailRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendingService {
    private final MailgunEmailService mailgunEmailService;
    private final  EmailTemplateService emailTemplateService;

    public void sendEmail(EmailRequestDto emailRequestDto){
        String body=emailTemplateService.createEmail(emailRequestDto.getTemplateName(),
                emailRequestDto.getTemplateModel());

        mailgunEmailService.sendHtmlEmail(emailRequestDto.getTo(),emailRequestDto.getSubject(),body);


    }


}
