package UserQuizManagement.demoUserQuiz.Service;

import UserQuizManagement.demoUserQuiz.DTO.DTO;
import UserQuizManagement.demoUserQuiz.DTO.TopicDTO;

import UserQuizManagement.demoUserQuiz.DTO.ScoreUserDTO;
import UserQuizManagement.demoUserQuiz.DTO.TopicScoreDTO;
import UserQuizManagement.demoUserQuiz.Entity.Topic;
import UserQuizManagement.demoUserQuiz.Repository.TopicRepository;
import UserQuizManagement.demoUserQuiz.Utils.Exceptions.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static UserQuizManagement.demoUserQuiz.Utils.Beans.Beans.map;

@Service
public class TopicService {
    private final TopicRepository topicRepository;
    @Autowired
    private ScoreService scoreService ;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<TopicDTO> getAllTopics() {
        return topicRepository.findAllTopics().stream().map(el -> map(el,TopicDTO.class)).collect(Collectors.toList());
    }

    public TopicDTO addTopic(Topic topic) {
        return map(topicRepository.save(topic),TopicDTO.class) ;
    }

    public Topic getTopic(Long topicId) throws NotFound {
        Optional<Topic> seachTopic = topicRepository.findTopicById(topicId) ;
        if(seachTopic.isPresent()){
            return seachTopic.get();
        }
        else throw new NotFound("Topic Not Found") ;
    }

    public List<TopicScoreDTO> getAllScoresByTopic(){
        List<TopicScoreDTO> topicScores =  topicRepository.findAll().stream().map(el -> map(el,TopicScoreDTO.class)).collect(Collectors.toList());
        topicScores.stream().forEach(el -> {
            if(el.getTopicScores() !=null )
            Collections.sort(el.getTopicScores(), new Comparator<ScoreUserDTO>() {
                        @Override
                        public int compare(ScoreUserDTO o1, ScoreUserDTO o2) {
                            return o2.getScore().compareTo(o1.getScore());
                        }
                    });
                }
            );
        return topicScores ;
    }

    public TopicScoreDTO getAllTopicScores(Long topicId) throws IllegalAccessException {
        Optional<Topic> topic = topicRepository.findById(topicId) ;

        if(topic.isPresent()){

            TopicScoreDTO topicScores = map(topic.get(),TopicScoreDTO.class) ;
            System.out.println(topicScores);

//            sort in desc
            if(topicScores.getTopicScores() != null) {
                Collections.sort(topicScores.getTopicScores(), new Comparator<ScoreUserDTO>() {
                    @Override
                    public int compare(ScoreUserDTO o1, ScoreUserDTO o2) {
                        return o2.getScore().compareTo(o1.getScore());
                    }
                });
            }
            return topicScores ;
        }
        else throw new IllegalAccessException("Invalid Topic Id") ;
    }
    @Transactional
    public TopicDTO updateTopic(Topic topic , Long topicId) throws NotFound {
        Optional <Topic> search = topicRepository.findById(topicId) ;

        if(search.isPresent()){
            if( topic.getTopicName() != null && search.get().getTopicName() != topic.getTopicName()){
                search.get().setTopicName(topic.getTopicName());
            }
            if( topic.getTopicDescription() != null && search.get().getTopicDescription() != topic.getTopicDescription()){
                search.get().setTopicDescription(topic.getTopicDescription());
            }
            return map(search.get(),TopicDTO.class) ;

        }
        else throw new NotFound("Topic Not Found") ;
    }

    public TopicDTO deleteTopic(Long topicId) throws NotFound {
        Optional <Topic> topic = topicRepository.findById(topicId) ;
        if(topic.isPresent()){
            topicRepository.deleteById(topicId);
            return map(topic,TopicDTO.class) ;
        }
        else throw new NotFound("Invalid Topic Id") ;
    }
}
