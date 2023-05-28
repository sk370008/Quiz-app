package com.example.QuizApp.RequestDTO;

import com.example.QuizApp.Enum.QuizStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class QuizRequestDto {

    private String question;

    private String[] options;

    private int ansIndex;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
