package com.example.oauth_demo.dto;

public class AuthResponse {

    private String token;

    // Constructor
    public AuthResponse() {
    }

    public AuthResponse(String token) {
        this.token = token;
    }

    // Getter
    public String getToken() {
        return token;
    }

    // Optionally, you can add a setter if you need to change the token after instantiation.
    public void setToken(String token) {
        this.token = token;
    }
}
