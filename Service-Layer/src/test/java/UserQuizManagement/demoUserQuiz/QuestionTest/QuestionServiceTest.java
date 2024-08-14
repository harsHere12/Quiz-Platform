//package UserQuizManagement.demoUserQuiz.QuestionTest;
//
//import UserQuizManagement.demoUserQuiz.Entity.Questions;
//import UserQuizManagement.demoUserQuiz.Repository.QuestionRepository;
//import UserQuizManagement.demoUserQuiz.Service.QuestionService;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import static org.junit.Assert.*;
//
//@SpringBootTest
//public class QuestionServiceTest {
//
//    @Autowired
//    QuestionService questionService;
//
//    @Test
//    public void addQuestion() throws IllegalAccessException {
//        Questions q= new Questions(1l,"description","option1","option2","option3","option4",5);
//
//        assertThrows(IllegalAccessException.class,()->{ questionService.addquestion(q);});
//    }
//
//}
