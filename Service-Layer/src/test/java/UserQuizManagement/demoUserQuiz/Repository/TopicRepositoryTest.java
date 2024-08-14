package UserQuizManagement.demoUserQuiz.Repository;

import UserQuizManagement.demoUserQuiz.Entity.Topic;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class TopicRepositoryTest {

    @Autowired
    TopicRepository topicRepository ;
    Topic topic = new Topic(1L,"React","React Excersize") ;
    Topic topic2 = new Topic(2L,"Angular","Angular") ;

    @BeforeEach
    public void beforeEach(){
        topicRepository.save(topic);
        topicRepository.save(topic2);
    }

//    @AfterEach
//    public void afterEach(){
//        topicRepository.deleteById(topic.getTopicId());
//        topicRepository.deleteById(topic2.getTopicId());
//    }

    @Test
    void findAllTopics() {
        Assert.assertEquals(Arrays.asList(topic,topic2),topicRepository.findAllTopics());
    }

    @Test
    void findTopicById() {
        Optional<Topic> actual = topicRepository.findTopicById(null);
        assertFalse(actual.isPresent());

        System.out.println(topicRepository.findById(topic2.getTopicId()));
        assertEquals(Optional.of(topic2),topicRepository.findById(topic2.getTopicId()));


    }
}