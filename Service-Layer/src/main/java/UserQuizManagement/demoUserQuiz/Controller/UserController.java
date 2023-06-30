package UserQuizManagement.demoUserQuiz.Controller;

import UserQuizManagement.demoUserQuiz.DTO.DTO;
import UserQuizManagement.demoUserQuiz.DTO.HttpToken;
import UserQuizManagement.demoUserQuiz.Entity.User;

import UserQuizManagement.demoUserQuiz.Service.ScoreService;
import UserQuizManagement.demoUserQuiz.Service.UserService;
import UserQuizManagement.demoUserQuiz.Utils.Exceptions.NotFound;
import UserQuizManagement.demoUserQuiz.Utils.Exceptions.UnauthorizedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;


import static UserQuizManagement.demoUserQuiz.Utils.Beans.Beans.checkAuthorisation ;
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ScoreService scoreService ;
    @GetMapping("/users")
    public List<DTO> getAllUser(@RequestHeader("access-token") String acessToken) throws Exception {
        System.out.println(acessToken);
        User user = checkAuthorisation(acessToken); ;
        if(!user.getRoles().equals("ADMIN")) throw new UnauthorizedUser("Your are not admin") ;
        List<DTO> users = new ArrayList<>() ;
        users.addAll(userService.getUsers());
        return users ;
    }

    @GetMapping("/users/id")
    public DTO getUserById(@RequestHeader("access-token") String accessToken) throws NotFound, AuthenticationException, UnauthorizedUser {
        System.out.println("users");
        User user = checkAuthorisation(accessToken);
        return userService.getUserById(user.getUserId());
    }

    @GetMapping("/users/scores")
    public List<DTO> getAllScores(@RequestHeader("access-token") String accessToken) throws AuthenticationException, UnauthorizedUser {

        User user = checkAuthorisation(accessToken);
        if(!user.getRoles().equals("ADMIN")) throw new UnauthorizedUser("Your are not admin") ;

        System.out.println(user);
        List <DTO> userScores = new ArrayList<>() ;
        userScores.addAll(userService.getAllScoresOfAllUsers()) ;
        return userScores ;
    }

    @GetMapping("/users/id/scores")
     public DTO getAllScoresOfUserById(@RequestHeader("access-token") String accessToken) throws NotFound, AuthenticationException, UnauthorizedUser {
        User user = checkAuthorisation(accessToken);
        return userService.getAllScoreOfUserById(user.getUserId()) ;
    }

}
