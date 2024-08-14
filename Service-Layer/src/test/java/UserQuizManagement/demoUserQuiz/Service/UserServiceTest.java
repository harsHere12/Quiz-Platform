package UserQuizManagement.demoUserQuiz.Service;

import UserQuizManagement.demoUserQuiz.DTO.UserDTO;
import UserQuizManagement.demoUserQuiz.DTO.UserScoreDTO;
import UserQuizManagement.demoUserQuiz.Entity.Score;
import UserQuizManagement.demoUserQuiz.Entity.Topic;
import UserQuizManagement.demoUserQuiz.Entity.User;
import UserQuizManagement.demoUserQuiz.Repository.ScoreRepository;
import UserQuizManagement.demoUserQuiz.Repository.TopicRepository;
import UserQuizManagement.demoUserQuiz.Repository.UserRepository;
import UserQuizManagement.demoUserQuiz.Utils.Exceptions.NotFound;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static UserQuizManagement.demoUserQuiz.Utils.Beans.Beans.map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService ;

    @Mock
    private UserRepository userRepository ;

    @Mock
    private ScoreRepository scoreRepository ;

    @Mock TopicService topicService ;

    @Mock TopicRepository topicRepository ;
//


    @Mock
    private ScoreService scoreService ;



    Score score = new Score(1L,80L, new Topic(1L,"React","React Excersize")) ;
    Score score2 = new Score(2L, 90L,new Topic("React","React Excersize")) ;

    Topic topic = new Topic(1L,"React","React Excersize") ;


    private User user = new User(1L,"Harsh","harsh@gmail.com", "ADMIN") ;
    private User user1 = new User(2L,"Adarsh","adarsh@gmail.com", "ADMIN") ;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user)) ;
        Assert.assertEquals(Arrays.asList(map(user, UserDTO.class)),userService.getUsers());

    }

    @Test
    void getUserById() throws NotFound {
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user)) ;
        assertEquals(map(user,UserDTO.class),userService.getUserById(user.getUserId()));

        assertThrows(NotFound.class,()-> userService.getUserById(null)) ;
    }

    @Test
    void getAllScoreOfUserById() throws NotFound {
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user)) ;
        user.setUserScores(Arrays.asList(score,score2));
        score.setUser(user);
        score2.setUser(user);
        when(scoreService.findAllScoresById(user.getUserId())).thenReturn(Arrays.asList(score,score2)) ;
        assertEquals(map(user,UserScoreDTO.class),userService.getAllScoreOfUserById(user.getUserId()));
        assertThrows(NotFound.class,()-> userService.getUserById(null)) ;
    }

    @Test
    void getAllScoresOfAllUsers() {


        when(userRepository.findAll()).thenReturn(Arrays.asList(user,user1)) ;

        score.setUser(user);
        score2.setUser(user);
        user.setUserScores(Arrays.asList(score,score2));
        when(scoreService.findAllScoresById(user.getUserId())).thenReturn(Arrays.asList(score,score2)) ;

        score.setUser(user1);
        score2.setUser(user1);
        user1.setUserScores(Arrays.asList(score,score2));
        when(scoreService.findAllScoresById(user1.getUserId())).thenReturn(Arrays.asList(score,score2)) ;

        assertEquals(Arrays.asList(map(user,UserScoreDTO.class),map(user1,UserScoreDTO.class)),userService.getAllScoresOfAllUsers());

    }

    @Test
    void submitScore() throws IllegalAccessException {
        when(userRepository.findById(null)).thenReturn(Optional.ofNullable(null)) ;
        assertThrows(IllegalAccessException.class,()-> userService.submitScore(null,topic.getTopicId(),60L)) ;

        when(topicService.getTopic(null)).thenReturn(null);
        assertThrows(IllegalAccessException.class,()-> userService.submitScore(user.getUserId(), null,60L)) ;

        when(userRepository.findById(user.getUserId())).thenReturn(Optional.ofNullable(user)) ;
        when(topicService.getTopic(topic.getTopicId())).thenReturn(topic) ;

        when(scoreService.getScore(user.getUserId(), topic.getTopicId())).thenReturn(null) ;
        userService.submitScore(user.getUserId(), topic.getTopicId(),100L);


        when(userRepository.findById(user1.getUserId())).thenReturn(Optional.ofNullable(user1)) ;
        when(scoreService.getScore(user1.getUserId(), topic.getTopicId())).thenReturn(score) ;
        userService.submitScore(user1.getUserId(), topic.getTopicId(),100L);

    }



}