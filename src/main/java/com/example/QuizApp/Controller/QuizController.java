package com.example.QuizApp.Controller;

import com.example.QuizApp.Model.Quiz;
import com.example.QuizApp.RequestDTO.QuizRequestDto;
import com.example.QuizApp.ResponseDTO.ActiveQuizResponseDto;
import com.example.QuizApp.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping
    public ResponseEntity<String> createQuiz(@RequestBody QuizRequestDto quizRequestDto) throws Exception {
        String msg = quizService.createQuiz(quizRequestDto);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<ActiveQuizResponseDto>> getActiveQuizzes(){
        List<ActiveQuizResponseDto> activeQuizzes = quizService.getActiveQuizzes();
        return new ResponseEntity<>(activeQuizzes,HttpStatus.OK);
    }




    @GetMapping("/{id}/result")
    public ResponseEntity<Integer> resultById(@PathVariable  int id) throws Exception {
        int correctOption = quizService.resultById(id);
        return new ResponseEntity<>(correctOption,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Quiz>> getAllQuizzes(){
        List<Quiz> quizResponseDtos = quizService.getAllQuizzes();
        return new ResponseEntity<>(quizResponseDtos,HttpStatus.OK);
    }
}



//Remaining stuff
// Implement rate-limiting to prevent abuse of the API.

//    A link to the Github repository containing your application code.
//        A link to the hosted API.
//        Video for API or postman collection with a deployed link.

