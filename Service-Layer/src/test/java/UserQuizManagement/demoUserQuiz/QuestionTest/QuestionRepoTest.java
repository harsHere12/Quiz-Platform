//package UserQuizManagement.demoUserQuiz.QuestionTest;
//
//import UserQuizManagement.demoUserQuiz.Entity.Questions;
//import UserQuizManagement.demoUserQuiz.Repository.QuestionRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.PageRequest;
//
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//@SpringBootTest
//public class QuestionRepoTest {
//
//    @Autowired
//    QuestionRepository questionRepository;
//
//
//    @Test
//    public void findByquestionIdTest(){
//
//        List<Questions> questionsList =questionRepository.findByquestionId(20l);
//
//        assertEquals(1,questionsList.size());
//    }
//
//    @Test
//    public void findBysubjectIdTest(){
//
//        List<Questions> questionsList = questionRepository.findBysubjectId(2l, PageRequest.of(0,1));
//
//        assertEquals(questionsList.size(),1);
//
//    }
//
//    @Test
//    public void countBySubjectIdTest() {
//        Long subjectID = 2l;
//
//        int count = questionRepository.countBysubjectId(subjectID);
//
//        //assertTrue();
//        assertTrue(count>0);
//    }
//}
