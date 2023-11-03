package com.example.aiport.dto;

public class ChangeRoleDto {
    private String login;
    private String role;
    public ChangeRoleDto() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
