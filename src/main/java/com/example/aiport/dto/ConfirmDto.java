package com.example.aiport.dto;

public class ConfirmDto {
    private Long id;
    private String answer;
    public ConfirmDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
