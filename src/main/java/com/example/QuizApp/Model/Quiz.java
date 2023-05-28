package com.example.QuizApp.Model;


import com.example.QuizApp.Enum.QuizStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.type.descriptor.converter.internal.ArrayConverter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String question;

    private String[] options;

    private int ansIndex;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private QuizStatus quizStatus;

}
