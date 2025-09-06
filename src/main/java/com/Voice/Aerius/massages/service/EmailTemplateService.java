package com.Voice.Aerius.massages.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailTemplateService {
    private final TemplateEngine templateEngine;

    public String createEmail (
             String templateName,
             Map<String,Object> templateModel

    ){
        Context context=new Context();
        context.setVariables(templateModel);
        return templateEngine.process(templateName,context);
    }


}
