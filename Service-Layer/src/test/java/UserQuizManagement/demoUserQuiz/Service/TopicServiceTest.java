package UserQuizManagement.demoUserQuiz.Service;

import UserQuizManagement.demoUserQuiz.DTO.TopicDTO;
import UserQuizManagement.demoUserQuiz.DTO.TopicScoreDTO;
import UserQuizManagement.demoUserQuiz.Entity.Score;
import UserQuizManagement.demoUserQuiz.Entity.Topic;
import UserQuizManagement.demoUserQuiz.Entity.User;
import UserQuizManagement.demoUserQuiz.Repository.ScoreRepository;
import UserQuizManagement.demoUserQuiz.Repository.TopicRepository;
import UserQuizManagement.demoUserQuiz.Utils.Exceptions.NotFound;
import javafx.beans.binding.When;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Optional;

import static UserQuizManagement.demoUserQuiz.Utils.Beans.Beans.map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class TopicServiceTest {

    @InjectMocks
    TopicService topicService ;

    @Mock
    TopicRepository topicRepository ;

    @Mock
    ScoreRepository scoreRepository ;

    Topic topic = new Topic(1L,"React","React Excersize") ;
    Topic topic2 = new Topic(2L,"Angular","Angular") ;

    Score score = new Score(80L, new User("Harsh","harsh@gmail.com"),topic) ;
    Score score2 = new Score(60L, new User("Harsh","harsh@gmail.com"),topic) ;

    @Test
    void getAllTopics() {
        when(topicRepository.findAllTopics()).thenReturn(Arrays.asList(topic,topic2)) ;
        assertEquals(Arrays.asList(map(topic, TopicDTO.class),map(topic2,TopicDTO.class)),topicService.getAllTopics());

    }

    @Test
    void addTopic() {
        when(topicRepository.save(topic)).thenReturn(topic) ;
        assertEquals(map(topic,TopicDTO.class),topicService.addTopic(topic));

    }

    @Test
    void getTopic() throws NotFound {
        when(topicRepository.findTopicById(topic.getTopicId())).thenReturn(Optional.of(topic)) ;
        assertEquals(topic ,topicService.getTopic(topic.getTopicId()));

        when(topicRepository.findTopicById(null)).thenReturn(Optional.ofNullable(null)) ;
        assertThrows(NotFound.class, ()->{ topicService.getTopic(null) ;}) ;
    }

    @Test
    void getAllScoresByTopic() {

        when(topicRepository.findAll()).thenReturn(Arrays.asList(topic,topic2)) ;
        assertEquals( Arrays.asList(map(topic,TopicScoreDTO.class),map(topic2,TopicScoreDTO.class)),topicService.getAllScoresByTopic());

        topic.setTopicScores(Arrays.asList(score,score2));
        topic2.setTopicScores(Arrays.asList(score,score2));
        when(topicRepository.findAll()).thenReturn(Arrays.asList(topic,topic2)) ;
        assertEquals( Arrays.asList(map(topic,TopicScoreDTO.class),map(topic2,TopicScoreDTO.class)),topicService.getAllScoresByTopic());
    }

    @Test
    void getAllTopicScores() throws IllegalAccessException {
        when(topicRepository.findById(topic.getTopicId())).thenReturn(Optional.ofNullable(topic)) ;
        assertEquals(map(topic,TopicScoreDTO.class),topicService.getAllTopicScores(topic.getTopicId()));

        topic.setTopicScores(Arrays.asList(score,score2));
        when(topicRepository.findById(topic.getTopicId())).thenReturn(Optional.ofNullable(topic)) ;
        assertEquals(map(topic,TopicScoreDTO.class),topicService.getAllTopicScores(topic.getTopicId()));

        when(topicRepository.findById(null)).thenReturn(Optional.ofNullable(null)) ;
        assertThrows(IllegalAccessException.class,()->{topicService.getAllTopicScores(null);});

    }

    TopicDTO updateTopicCases(Topic topic){
        when(topicRepository.findById(1L)).thenReturn(Optional.ofNullable(topic)) ;
        topic.setTopicName(topic2.getTopicName()) ;
        topic.setTopicDescription(topic2.getTopicDescription());
        return map(topic,TopicDTO.class) ;

    }

    @Test
    void updateTopic() throws NotFound {
        when(topicRepository.findById(null)).thenReturn(Optional.ofNullable(null)) ;
        assertThrows(NotFound.class,()->topicService.updateTopic(null,null));


        assertEquals(updateTopicCases(topic),topicService.updateTopic(topic2,topic.getTopicId()));
        topic.setTopicName(null);
        assertEquals(updateTopicCases(topic),topicService.updateTopic(topic2,topic.getTopicId()));
        topic.setTopicDescription(null);
        assertEquals(updateTopicCases(topic),topicService.updateTopic(topic2,topic.getTopicId()));


    }

    @Test
    void deleteTopic() throws NotFound {
        when(topicRepository.findById(null)).thenReturn(Optional.ofNullable(null)) ;
        assertThrows(NotFound.class,()->topicService.deleteTopic(null));

        when(topicRepository.findById(1L)).thenReturn(Optional.ofNullable(topic)) ;
        assertEquals(map(topic,TopicDTO.class),topicService.deleteTopic(topic.getTopicId()));

    }
}