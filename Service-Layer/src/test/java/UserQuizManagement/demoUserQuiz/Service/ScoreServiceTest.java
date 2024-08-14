package UserQuizManagement.demoUserQuiz.Service;

import UserQuizManagement.demoUserQuiz.Entity.Score;
import UserQuizManagement.demoUserQuiz.Entity.Topic;
import UserQuizManagement.demoUserQuiz.Entity.User;
import UserQuizManagement.demoUserQuiz.Repository.ScoreRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class ScoreServiceTest {

    @InjectMocks
    private ScoreService scoreService ;
    @Mock
    private ScoreRepository scoreRepository ;

    @Test
    void getAllScores() {
        Score score = new Score(80L, new User("Harsh","harsh@gmail.com"),new Topic("React","React Excersize")) ;
        Score score2 = new Score(80L, new User("Kash","akash@gmail.com"),new Topic("React","React Excersize")) ;
        when(scoreService.getAllScores()).thenReturn(Arrays.asList(score,score2)) ;
        Assert.assertEquals(Arrays.asList(score,score2),scoreService.getAllScores());
    }

    @Test
    void findAllScoresById() {
        // Flow 1
        when(scoreService.findAllScoresById(1L)).thenReturn(Collections.singletonList(new Score(10L, new User("harsh", "harsh@gmail.com"))));
        Assert.assertEquals(scoreService.findAllScoresById(1L),Collections.singletonList(new Score(10L, new User("harsh", "harsh@gmail.com"))))    ;
        // Flow 1
        when(scoreService.findAllScoresById(null)).thenReturn(new ArrayList<>()) ;
        Assert.assertEquals(scoreService.findAllScoresById(null),new ArrayList<>());
    }

    @Test
    void addScore() {
        Score score = new Score(80L, new User("Harsh","harsh@gmail.com"),new Topic("React","React Excersize")) ;
        when(scoreService.addScore(score)).thenReturn(score) ;
        Assert.assertEquals(scoreService.addScore(score),score);
        score = null ;
        Assert.assertEquals(scoreService.addScore(score),score);

    }

    @Test
    void getScore() {

        //flow 1
        Score score = new Score(80L, new User("Harsh","harsh@gmail.com"),new Topic("React","React Excersize")) ;
        when(scoreRepository.findScore(score.getUser().getUserId(),score.getTopic().getTopicId())).thenReturn(Arrays.asList(score)) ;
        Assert.assertEquals(scoreService.getScore(score.getUser().getUserId(),score.getTopic().getTopicId()),score);

        //flow 2
        when(scoreRepository.findScore(score.getUser().getUserId(),null)).thenReturn(Arrays.asList()) ;
        Assert.assertEquals(scoreService.getScore(score.getUser().getUserId(),score.getTopic().getTopicId()),null);


    }
}