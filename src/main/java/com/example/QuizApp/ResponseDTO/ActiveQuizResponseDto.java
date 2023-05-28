package com.example.QuizApp.ResponseDTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActiveQuizResponseDto {

    private String question;

    private String[] options;

    private int ansIndex;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
