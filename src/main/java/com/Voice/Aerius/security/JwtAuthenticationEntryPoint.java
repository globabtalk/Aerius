package com.Voice.Aerius.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception
    ) throws IOException, ServletException{
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Map<String,Object> jsonBody=new LinkedHashMap<>();
        jsonBody.put("status", HttpStatus.UNAUTHORIZED.value());
        jsonBody.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        jsonBody.put("message", exception.getMessage());
        jsonBody.put("path", request.getRequestURI());
        objectMapper.writeValue(response.getWriter(),jsonBody);

    }

}
