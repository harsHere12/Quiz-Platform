package UserQuizManagement.demoUserQuiz.Controller;


import UserQuizManagement.demoUserQuiz.DTO.MessageDTO;
import UserQuizManagement.demoUserQuiz.DTO.QuestionDTO;
import UserQuizManagement.demoUserQuiz.Entity.Questions;
import UserQuizManagement.demoUserQuiz.Entity.User;
import UserQuizManagement.demoUserQuiz.Service.QuestionService;

import UserQuizManagement.demoUserQuiz.Service.UserService;
import UserQuizManagement.demoUserQuiz.Utils.Exceptions.UnauthorizedUser;
import UserQuizManagement.demoUserQuiz.Utils.QuestionHelper;
import UserQuizManagement.demoUserQuiz.Utils.UserSubjectHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static UserQuizManagement.demoUserQuiz.Utils.Beans.Beans.checkAuthorisation;
import static org.junit.Assert.assertEquals;

@RestController
@RequestMapping(path="questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    /*public void checkAuth(String accessToken) throws  Exception{
        RestTemplate restTemplate = new RestTemplate();
        String ResourceUrl = "http://10.113.113.44:8083/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+accessToken);

        HttpEntity<String> entity = new HttpEntity<String>(headers);
        //String result = restTemplate.exchange(ResourceUrl,HttpMethod.GET, entity, String.class);

        ResponseEntity<String> response = restTemplate.exchange(ResourceUrl, HttpMethod.GET,entity,String.class);
        System.out.println(response.getBody());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }*/

    @PostMapping()
    public void addQuestion(@RequestHeader("access-token") String accessToken,@RequestBody Questions question) throws Exception {

        User user =checkAuthorisation(accessToken);

        String role = user.getRoles();

        if(!role.equals("ADMIN")){
            throw new UnauthorizedUser("Only admin can upload Questions");
        }

        if(question.getCorrectAnswer()<1 ||question.getCorrectAnswer()>4){
            throw new IllegalStateException("Correct answer should be between 1-4");
        }
        questionService.addquestion(question);
        //System.out.println(question);
    }

    @GetMapping()
    public List<Questions> getAllQuestions(@RequestHeader("access-token") String accessToken) throws Exception {

        System.out.println(accessToken);

        User user =checkAuthorisation(accessToken);

        String role = user.getRoles();

        if(!role.equals("ADMIN")){
            throw new UnauthorizedUser("Only admin can upload Questions");
        }

        //System.out.println(user);
        return questionService.getAllQuestions();
    }

    @PostMapping(path="/checkanswers/{questionId}/{givenAnswer}")
    public int checkAnswers(@RequestHeader("access-token") String accessToken,@PathVariable Long questionId,@PathVariable int givenAnswer) throws Exception {

        User user =checkAuthorisation(accessToken);

        Long userId = user.getUserId();

        int marks = questionService.checkAnswer(userId,questionId,givenAnswer);

        return marks;

    }

    @GetMapping(path="/getquestions/{subject_id}")
    public QuestionDTO getQuestions(@RequestHeader("access-token") String accessToken,@PathVariable Long subject_id ) throws Exception {

        User user =checkAuthorisation(accessToken);

        Long userId = user.getUserId();

        Questions question= questionService.generateQuestions(userId,subject_id);
        QuestionDTO questionDTO = new QuestionDTO(question);
        return questionDTO;
    }

    @PostMapping(path="/upload/")
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file,@RequestHeader("access-token") String accessToken) throws Exception {

        User user =checkAuthorisation(accessToken);

        String role = user.getRoles();

        if(!role.equals("ADMIN")){
            throw new IllegalStateException("Only admin can upload Questions");
        }


        if(file.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No file found");
        }

        /*System.out.println(file.getName());
        System.out.println(file.getSize());
        System.out.println(file.getContentType());*/

        if(!file.getContentType().equals("application/json")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File must be json");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        List<Questions> example = objectMapper.readValue(file.getInputStream(), new TypeReference<List<Questions>>(){});

        questionService.saveAllQuestions(example);
        System.out.println(example);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("OK");
    }

    @PostMapping("/endTest/{subjectId}")
    public MessageDTO endTest(@PathVariable Long subjectId, @RequestHeader("access-token") String accessToken) throws Exception {

        User user =checkAuthorisation(accessToken);

        Long userId = user.getUserId();

        UserSubjectHelper keyObject= new UserSubjectHelper(userId,subjectId);
        if(!questionService.questionMap.containsKey(keyObject)){
            throw new IllegalAccessException("Probably test for this subject is not started....");
        }

        QuestionHelper valueObject = questionService.questionMap.get(keyObject);



        int score = valueObject.getScore();

        System.out.println(score);

        userService.submitScore(userId,subjectId,(long)score);

        questionService.questionMap.remove(keyObject);

        MessageDTO message = new MessageDTO("Your score for this test is "+score);

        return message;
    }

}
