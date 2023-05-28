package com.example.QuizApp.Service;

import com.example.QuizApp.Enum.QuizStatus;
import com.example.QuizApp.Model.Quiz;
import com.example.QuizApp.Repository.QuizRepository;
import com.example.QuizApp.RequestDTO.QuizRequestDto;
import com.example.QuizApp.ResponseDTO.ActiveQuizResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    public String createQuiz(QuizRequestDto quizRequestDto) throws Exception {

        //validate all the details like string question should not be empty,
        //options should be more than 1 , valid answer Index and dates should not be null
        if(quizRequestDto.getQuestion().equals("") || quizRequestDto.getOptions().length <=1 ||
        quizRequestDto.getAnsIndex()<=-1 || quizRequestDto.getStartDate().equals(null) || quizRequestDto.getEndDate().equals(null)){
            throw new Exception("Validate Inserted details");
        }

        //Create quiz object
        Quiz quiz = new Quiz();
        //set all values from reauest dto
        quiz.setQuestion(quizRequestDto.getQuestion());
        String[] options = quizRequestDto.getOptions();
        quiz.setOptions(options);
        quiz.setAnsIndex(quizRequestDto.getAnsIndex());
        quiz.setStartDate(quizRequestDto.getStartDate());
        quiz.setEndDate(quizRequestDto.getEndDate());
        //check for enum and set the enum value
        if(statusCheck(quizRequestDto.getStartDate(),quizRequestDto.getEndDate())){
            quiz.setQuizStatus(QuizStatus.ACTIVE);
        }else{
            quiz.setQuizStatus(QuizStatus.INACTIVE);
        }
        quizRepository.save(quiz);
        return "Created successfully";
    }


    //to check the status of quiz
    public boolean statusCheck(LocalDateTime startDate, LocalDateTime endDate){
        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isAfter(startDate) && currentTime.isBefore(endDate)){
            return true;
        }
        return false;
    }

    public List<ActiveQuizResponseDto> getActiveQuizzes() {

        List<Quiz> quizList = quizRepository.findAll();
        setStatus(quizList);
        List<ActiveQuizResponseDto> activeQuizResponseDtoList = new ArrayList<>();
        for (Quiz quiz : quizList){
            if (statusCheck(quiz.getStartDate(),quiz.getEndDate())){
                //preparing response
                ActiveQuizResponseDto activeQuizResponseDto = new ActiveQuizResponseDto();
                activeQuizResponseDto.setQuestion(quiz.getQuestion());
                activeQuizResponseDto.setOptions(quiz.getOptions());
                activeQuizResponseDto.setAnsIndex(quiz.getAnsIndex());
                activeQuizResponseDto.setStartDate(quiz.getStartDate());
                activeQuizResponseDto.setEndDate(quiz.getEndDate());
                activeQuizResponseDtoList.add(activeQuizResponseDto);

            }
        }

        return activeQuizResponseDtoList;
    }

    //setting status before sending active quizzes
    public void setStatus(List<Quiz> quizList){
        for (Quiz quiz : quizList){

            if (statusCheck(quiz.getStartDate(),quiz.getEndDate())){
                quiz.setQuizStatus(QuizStatus.ACTIVE);
            }else {
                quiz.setQuizStatus(QuizStatus.INACTIVE);
            }
            if (quiz.getEndDate().isBefore(LocalDateTime.now())){
                quiz.setQuizStatus(QuizStatus.FINISHED);
            }
            quizRepository.save(quiz);
        }
    }

    //Cron Job to change status(The status will change at 12:00:00pm everyday)
    @Scheduled(cron = "0 0 12 * * ?")
    public void cronStatusCheck(){
        Logger logger = Logger.getLogger(getClass().getName());
        List<Quiz> quizList = quizRepository.findAll();
        logger.log(Level.INFO, "Number of active quizzes found: ");
        setStatus(quizList);
    }


    //return ans by Id
    public int resultById(int id) throws Exception {
        Quiz quiz;
        try {
            quiz = quizRepository.findById(id).get();
        }catch (Exception e){
            throw new Exception("Invalid Id");
        }
        //ans will be the next option as we are using 0 based indexing array
        return quiz.getAnsIndex()+1;
    }

    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizList = quizRepository.findAll();
        return quizList;
    }
}
