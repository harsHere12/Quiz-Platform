package UserQuizManagement.demoUserQuiz.Service;

import UserQuizManagement.demoUserQuiz.DTO.UserDTO;
import UserQuizManagement.demoUserQuiz.DTO.UserScoreDTO;
import UserQuizManagement.demoUserQuiz.Entity.Score;
import UserQuizManagement.demoUserQuiz.Entity.Topic;
import UserQuizManagement.demoUserQuiz.Entity.User;

import UserQuizManagement.demoUserQuiz.Repository.UserRepository;
import UserQuizManagement.demoUserQuiz.Utils.Exceptions.NotFound;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static UserQuizManagement.demoUserQuiz.Utils.Beans.Beans.map;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScoreService scoreService ;
    @Autowired
    private TopicService topicService ;
    public List<UserDTO> getUsers(){
        List<UserDTO> userDTOs = userRepository.findAll().stream().map(el -> map(el,UserDTO.class)).collect(Collectors.toList());
        return userDTOs ;
    }
    public UserDTO getUserById(Long user_id) throws NotFound {
        Optional<User> userOptional = userRepository.findById(user_id);
        if(!userOptional.isPresent()){
            throw new NotFound("User Not Found") ;

        }
        ModelMapper modelMapper = new ModelMapper() ;
        UserDTO userDTO = modelMapper.map(userOptional.get(),UserDTO.class) ;
        return userDTO;
    }


//    public Users createNewUser(Users user)  {
//        boolean optional= userRepository.existsByUserEmail(user.getUserEmail());
//        if(optional){
//            throw new IllegalArgumentException("Email ID already exists !");
//        }
//        return userRepository.save(user);
//    }


    public UserScoreDTO getAllScoreOfUserById(Long userId) throws NotFound {
        Optional<User> user = userRepository.findById(userId) ;
        System.out.println(scoreService) ;
        System.out.println(user);

        if(user.isPresent()){
            user.get().setUserScores(scoreService.findAllScoresById(userId));
            UserScoreDTO userScore = map(user.get(),UserScoreDTO.class);
            return userScore ;
        }
        else throw new NotFound("User Not Found") ;
    }

    public List<UserScoreDTO> getAllScoresOfAllUsers(){
        List<User> users = userRepository.findAll() ;
        System.out.println(users);
        for(User user : users){
            user.setUserScores(scoreService.findAllScoresById(user.getUserId()));
        };
        return users.stream().map(el -> map(el,UserScoreDTO.class)).collect(Collectors.toList()) ;
    }

    @Transactional
    public void submitScore(Long userId, Long topicId, Long score) throws IllegalAccessException {
        Optional<User> user = userRepository.findById(userId) ;
        if(user.isPresent()){
            Optional<Topic> topic = Optional.ofNullable(topicService.getTopic(topicId));
            if(topic.isPresent()){
                Score previousScore = scoreService.getScore(userId,topicId) ;
                System.out.println(previousScore);
                if(previousScore == null){
                    scoreService.addScore(new Score(score,user.get(),topic.get()));
                }
                else{
                    previousScore.setScore(Math.max(score,previousScore.getScore()));
                }
            } else throw new IllegalAccessException("Invalid topic Id") ;
        }
        else throw new IllegalAccessException("Invalid User Id");
    }
}
