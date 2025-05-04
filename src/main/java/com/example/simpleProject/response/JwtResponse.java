package com.example.simpleProject.response;

public class JwtResponse {
    private String token;
    private final String type = "Bearer";
    private UserResponse user;

    public JwtResponse(String token, UserResponse user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }


}
