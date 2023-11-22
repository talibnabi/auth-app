package com.project.auth.model.dto.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {

    private String token;

}
