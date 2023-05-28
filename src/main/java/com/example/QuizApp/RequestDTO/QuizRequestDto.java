package com.example.QuizApp.RequestDTO;

import com.example.QuizApp.Enum.QuizStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizRequestDto {

    private String question;

    private String[] options;

    private int ansIndex;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
