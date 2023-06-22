package com.example.multiplechoiceexam.dto.auth;

public class AuthenticationResponse {
    private String message;
    private String username;
    private String email;
    private String access_token;
    private String refresh_token;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String message, String username, String email, String access_token, String refresh_token) {
        this.message = message;
        this.username = username;
        this.email = email;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
