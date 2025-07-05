package com.Voice.Aerius.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse<T> {
    private int statusCode;
    private String status;
    private String message;
    private T data;



}