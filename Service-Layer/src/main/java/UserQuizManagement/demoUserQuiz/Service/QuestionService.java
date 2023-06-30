package UserQuizManagement.demoUserQuiz.Service;

import UserQuizManagement.demoUserQuiz.Entity.Questions;
import UserQuizManagement.demoUserQuiz.Repository.QuestionRepository;
import UserQuizManagement.demoUserQuiz.Repository.ScoreRepository;
import UserQuizManagement.demoUserQuiz.Utils.QuestionHelper;
import UserQuizManagement.demoUserQuiz.Utils.UserSubjectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import java.util.*;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    public Map<UserSubjectHelper, QuestionHelper> questionMap;

    public QuestionService(){
        questionMap = new HashMap<>();
    }


    public void addquestion(Questions question) throws Exception {
        /*
         Recieves: question Object
         Action: Adds question to database
         */
        if(question.getCorrectAnswer()<1 || question.getCorrectAnswer()>4 ){
            throw new IllegalAccessException("Answer should be between 1-4");
        }

        System.out.println(question);
        questionRepository.save(question);

    }




    public int checkAnswer(Long userId,Long questionId, int givenAnswer) throws Exception {


        List<Questions> quesstionInList = questionRepository.findByquestionId(questionId);

        Questions question = quesstionInList.get(0);

        Long subjectId =question.getSubjectId();

        int marks=0;

        if(question.getCorrectAnswer()==givenAnswer){
            marks=10;
            UserSubjectHelper keyObject= new UserSubjectHelper(userId,subjectId);
            QuestionHelper valueObject= questionMap.get(keyObject);
            valueObject.setScore(valueObject.getScore()+10);
            questionMap.put(keyObject,valueObject);

        }else{
            System.out.println("Wrong Answer");
        }

        return marks;

    }


    public Questions generateQuestions(Long userId, Long subjectId) throws IllegalAccessException {


        UserSubjectHelper userSubject = new UserSubjectHelper(userId,subjectId);

        String errormessage="";
        int error=0;

        if(!questionMap.containsKey(userSubject)){ //user-subject not present
            int totalQuestions = questionRepository.countBysubjectId(subjectId);
            QuestionHelper questionHelper = new QuestionHelper(totalQuestions);
            questionMap.put(userSubject,questionHelper);

            if(totalQuestions<10){
                error=1;
                questionMap.remove(userSubject);
                errormessage="Number of questions in subject should be more than 10";
            }
        }
        else{  // user-subeject present
            QuestionHelper questionHelper =questionMap.get(userSubject);
            questionHelper.setQuestionIndex(questionHelper.getQuestionIndex()+1);
            questionMap.put(userSubject,questionHelper);
            if(questionHelper.getQuestionIndex()>=10){
                error=2;
                questionMap.remove(userSubject);
                errormessage="Attempted more than 10 questions, Total marks= "+questionHelper.getScore();
            }
        }
        if(error!=0){
            throw new IllegalAccessException(errormessage);
        }


        System.out.println("INSIDE FUNCTION");

        QuestionHelper questionHelper = questionMap.get(userSubject);

        int offset = questionHelper.getPageNo()*questionHelper.getPageSize()
                 + questionHelper.getOffset().get(questionHelper.getQuestionIndex());


        Questions question = null;
        try {
            List<Questions> questionList = questionRepository.findBysubjectId(subjectId, PageRequest.of(offset, 1));
            question = questionList.get(0);
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("Offset more than questions");
        }

        //System.out.println(question);

        return question;
    }


    public List<Questions> getAllQuestions(){

        return  questionRepository.findAll();
    }

    public void saveAllQuestions(List<Questions> example) {

        questionRepository.saveAll(example);

    }

}
