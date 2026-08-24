package com.example._3userauthsystem.dto;

public class AuthResponse {
    private String token;
    private String tokenType = "Bearer";
    private String message;

    public AuthResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public String getToken()      { return token; }
    public String getTokenType()  { return tokenType; }
    public String getMessage()    { return message; }
}
