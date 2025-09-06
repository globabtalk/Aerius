package com.Voice.Aerius.massages.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class EmailRequestDto implements Serializable {

    private String to;

    private String subject;

    private String templateName;

    private Map<String,Object> templateModel;

}
