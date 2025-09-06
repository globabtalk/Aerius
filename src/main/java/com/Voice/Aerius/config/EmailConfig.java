package com.Voice.Aerius.config;

import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.client.MailgunClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EmailConfig {
    @Value("${mailgun.api-key}")
    private String mailgunApiKey;

    @Getter
    @Value("${mailgun.domain}")
    private String MailgunDomain;


    @Getter
    @Value("${mailgun.from-email}")
    private String MailgunFromEmail;

    @Bean
    public MailgunMessagesApi mailgunMessagesApi(){
        return MailgunClient.config(mailgunApiKey).createApi(MailgunMessagesApi.class);
    }

}
