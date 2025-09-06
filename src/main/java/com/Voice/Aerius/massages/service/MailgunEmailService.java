package com.Voice.Aerius.massages.service;

import com.Voice.Aerius.config.EmailConfig;
import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.exception.MailGunException;
import com.mailgun.model.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailgunEmailService {
  private final MailgunMessagesApi mailgunMessagesApi;
  private final EmailConfig emailConfig;

  public void sendHtmlEmail(
          String to,
          String subject,
          String body
  )
  {
      Message message= Message.builder()
              .from(emailConfig.getMailgunFromEmail())
              .to(to).subject(subject).html(body).build();
      try{
          mailgunMessagesApi.sendMessage(emailConfig.getMailgunDomain(),message );

      } catch (MailGunException mailGunException) {
          throw new RuntimeException("Failed to send HTML email: " + mailGunException.getMessage(),mailGunException);
      }
  }
}
