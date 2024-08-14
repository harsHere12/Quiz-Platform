package UserQuizManagement.demoUserQuiz.Repository;

import UserQuizManagement.demoUserQuiz.Entity.Score;
import UserQuizManagement.demoUserQuiz.Entity.Topic;
import UserQuizManagement.demoUserQuiz.Entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class ScoreRepositoryTest {

    Score score = new Score(1L,60L, new Topic(1L,"React","React React Excersize"), new User(1L,"harsh","harsh@gmail.com")) ;

    @Autowired
    UserRepository userRepository ;

    @Autowired
    TopicRepository topicRepository ;

    @Autowired
    ScoreRepository scoreRepository ;


    @BeforeEach
    public void BeforeEach(){

        userRepository.save(score.getUser()) ;
        topicRepository.save(score.getTopic()) ;
        scoreRepository.save(score) ;

    }


    @Test
    void findAllScoresById() {
        assertEquals(new ArrayList<>(),scoreRepository.findAllScoresById(null));
        assertEquals(Arrays.asList(score),scoreRepository.findAllScoresById(score.getUser().getUserId()));

    }

    @Test
    void findScore() {
        assertEquals(new ArrayList<>(),scoreRepository.findScore(null,null));
        assertEquals(Arrays.asList(score),scoreRepository.findScore(score.getUser().getUserId(),score.getTopic().getTopicId()));

    }
}