package com.work.motel.domain.entities;

public class LoginRequest {

    private String email;
    private String password;

    // Construtor padrão
    public LoginRequest() {
    }

    // Construtor com parâmetros (opcional)
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters e Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest [email=" + email + ", password=" + password + "]";
    }
}
