package com.shaurya.Ecommerce_sb.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {
    private Long id;
    private String jwtToken;

    private String username;
    private List<String> roles;

    public LoginResponse(Long id, String username, List<String> roles, String jwtToken) {
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.jwtToken = jwtToken;
    }
}


