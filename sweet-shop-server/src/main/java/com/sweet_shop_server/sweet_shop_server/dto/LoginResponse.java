package com.sweet_shop_server.sweet_shop_server.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter 
@Setter
public class LoginResponse {
    private String accessToken;
    private String refreshToken;

    public LoginResponse(String accessToken) {
    }
}
