package UserQuizManagement.demoUserQuiz.Controller;


import UserQuizManagement.demoUserQuiz.DTO.TopicDTO;

import UserQuizManagement.demoUserQuiz.DTO.DTO;
import UserQuizManagement.demoUserQuiz.Entity.Topic;
import UserQuizManagement.demoUserQuiz.Entity.User;
import UserQuizManagement.demoUserQuiz.Service.TopicService;
import UserQuizManagement.demoUserQuiz.Utils.Exceptions.NotFound;
import UserQuizManagement.demoUserQuiz.Utils.Exceptions.UnauthorizedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

import static UserQuizManagement.demoUserQuiz.Utils.Beans.Beans.checkAuthorisation;
import static UserQuizManagement.demoUserQuiz.Utils.Beans.Beans.map;

@RestController
@RequestMapping("topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public List<DTO> getAllTopics(){
        ArrayList<DTO>  topics = new ArrayList<>() ;
        topics.addAll(topicService.getAllTopics()) ;
        return topics ;
    }

    @GetMapping(path = "{topicId}")
    public DTO getTopic(@PathVariable ("topicId") Long topicId) throws NotFound {
        return map(topicService.getTopic(topicId) , TopicDTO.class) ;
    }

    @GetMapping(path = "/scores")
    public List<DTO> getAllScoresByTopic(@RequestHeader("access-token") String accessToken) throws AuthenticationException, UnauthorizedUser {
        User user = checkAuthorisation(accessToken);
        List<DTO> scores = new ArrayList<>() ;
        scores.addAll(topicService.getAllScoresByTopic()) ;
        return scores ;
    }
    @GetMapping(path = "/{topicId}/scores")
    public DTO getAllTopicScores(@RequestHeader("access-token") String accessToken , @PathVariable (value = "topicId") Long topicId) throws IllegalAccessException, AuthenticationException {
        User user = checkAuthorisation(accessToken);
        System.out.println(user);
        return topicService.getAllTopicScores(topicId);

    }

    @PostMapping
    public DTO addSubject(@RequestBody Topic topic , @RequestHeader("access-token") String accessToken ) throws AuthenticationException, UnauthorizedUser {
        User user = checkAuthorisation(accessToken);
        if(!user.getRoles().equals("ADMIN")) throw new UnauthorizedUser("You are Not an admin") ;
        return topicService.addTopic(topic) ;
    }

    @PutMapping("/{topicId}")
    public DTO updateTopic(@RequestHeader("access-token") String accessToken , @PathVariable ("topicId") Long topicId , @RequestBody Topic topic ) throws NotFound, AuthenticationException, UnauthorizedUser {

        User user = checkAuthorisation(accessToken);
        if(!user.getRoles().equals("ADMIN")) throw new UnauthorizedUser("Your are not admin") ;

        if(topic.getTopicId()!=null && topic.getTopicId()!=topicId) throw new IllegalArgumentException("Invalid Operation ");
        return topicService.updateTopic(topic, topicId) ;
    }

    @DeleteMapping("/{topicId}")
    public DTO deleteTopic(@RequestHeader("access-token") String accessToken ,@PathVariable ("topicId") Long topicId) throws NotFound, AuthenticationException, UnauthorizedUser {

        User user = checkAuthorisation(accessToken);
        if(!user.getRoles().equals("ADMIN")) throw new UnauthorizedUser("Your are not admin") ;

        return topicService.deleteTopic(topicId) ;
    }


}
