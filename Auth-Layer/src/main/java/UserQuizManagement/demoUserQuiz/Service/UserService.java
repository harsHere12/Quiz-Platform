package UserQuizManagement.demoUserQuiz.Service;

import UserQuizManagement.demoUserQuiz.CustomException;
import UserQuizManagement.demoUserQuiz.DTO.ForgotPasswordDto;
import UserQuizManagement.demoUserQuiz.DTO.UpdateDTO;
import UserQuizManagement.demoUserQuiz.Entity.Users;
import UserQuizManagement.demoUserQuiz.ExceptionHandler.Exceptions.EmailAlreadyExists;
import UserQuizManagement.demoUserQuiz.ExceptionHandler.Exceptions.NotFound;
import UserQuizManagement.demoUserQuiz.ExceptionHandler.Exceptions.PasswordOrEmailDoesNotMatches;
import UserQuizManagement.demoUserQuiz.ExceptionHandler.Exceptions.YouAreNotAuthorised;
import UserQuizManagement.demoUserQuiz.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;


@Service
public class UserService
{
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public List<Users> getUsers() throws YouAreNotAuthorised {
//        Optional<Users> usersOptional = userRepository.findByRoles(principal.getName());
//        Optional<Users> usersOptional = userRepository.findByRoles(principal.getName());
//        System.out.println(usersOptional);
        return userRepository.findAll();
    }
    public Users getUserById(Long userId) throws NotFound {
        Optional<Users> userOptional = userRepository.findById(userId);
        if(!userOptional.isPresent()){
            throw new NotFound("ID not found");
        }
        return userOptional.get();
    }



//    public static String encodeToBase64(String message) {
//        return Base64.getEncoder().encodeToString(message.getBytes());
//    }

    public Users createNewUser(Users user) throws Exception {
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        user.setroles("USER");
        boolean optional= userRepository.existsByUserEmail(user.getUserEmail());
        if(optional){
            throw new CustomException("Email ID already exists !");
        }
        return userRepository.save(user);
    }

    public Users createNewAdmin(Users user) throws Exception {
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        user.setroles("ADMIN");
        boolean optional= userRepository.existsByUserEmail(user.getUserEmail());
        if(optional){
            throw new EmailAlreadyExists("This Admin Email ID already exists !");
        }
        return userRepository.save(user);
    }

//    public Users loginUser(Users users) throws PasswordOrEmailDoesNotMatches {
//        Optional<Users> usersOptional = userRepository.findByUserEmail(users.getUserEmail());
//        String password= users.getUserPassword();
////        String encodedPwd=encodeToBase64(password);
//        if ((usersOptional.get().getUserPassword()).equals(passwordEncoder.encode(password))){
//            return usersOptional.get();
//        }
//        else throw  new PasswordOrEmailDoesNotMatches("Password does not match");
//    }

//    public Users adminUser(Users users) throws CustomException {
//        Optional<Users> usersOptional = userRepository.findByUserEmail(users.getUserEmail());
//        String password= users.getUserPassword();
//        String encodedPwd=encodeToBase64(password);
//        if (usersOptional.get().getRoles().equals(Roles.ADMIN))
//        {
//            if ((usersOptional.get().getUserPassword()).equals(encodedPwd)) {
//                System.out.println("Password Matched");
//            }
//            return usersOptional.get();
//        }
//        else throw  new CustomException("Password does not match");
//    }


    public Users forgotPassword(ForgotPasswordDto forgotPasswordDto) throws CustomException, NotFound {
        Optional<Users> usersOptional = userRepository.findByUserEmail(forgotPasswordDto.getUserEmail());
        if (!usersOptional.isPresent()){
            throw new NotFound("Email does not exists");
        }
        String question = usersOptional.get().getQuestion();
        String answer = usersOptional.get().getAnswer();
        if (!answer.equals(forgotPasswordDto.getAnswer())
                || !usersOptional.get().getUserEmail().equals(forgotPasswordDto.getUserEmail())) {
            throw new CustomException("The security answer or email or security question does not matches");
        }
        usersOptional.get().setUserPassword(passwordEncoder.encode(forgotPasswordDto.getNewPassword()));

        Users users1= userRepository.save(usersOptional.get());
        return users1;
    }

    public Users updateUser(UpdateDTO user, Principal principal) throws CustomException, EmailAlreadyExists {
        Optional<Users> userOptional = userRepository.findByUserEmail(principal.getName());
        if(userRepository.existsByUserEmail(user.getUserEmail())) {
            throw new CustomException("This email already exits try a new email.");
        }
        if(!userOptional.isPresent()){
            throw new EmailAlreadyExists("No such Email exists");
        }
        if(user.getUserName() != null) {
            userOptional.get().setUserName(user.getUserName());
        }
        if(user.getUserPhoneNo() != null) {
            userOptional.get().setUserPhoneNo(user.getUserPhoneNo());
        }
        if(user.getUserEmail() != null) {
            userOptional.get().setUserEmail(user.getUserEmail());
        }
        if (user.getDob()!=null){
            userOptional.get().setDob(user.getDob());
        }
        if (user.getQuestion()!=null){
            userOptional.get().setQuestion(user.getQuestion());
        }
        if (user.getAnswer()!=null){
            userOptional.get().setAnswer(user.getAnswer());
        }
        Users user1 = userRepository.save(userOptional.get());
        return user1;


    }
    public Map<String,Boolean> deleteUser(Long user_id) throws CustomException{
        Optional<Users> userOptional = userRepository.findById(user_id);
        if(!userOptional.isPresent()){
            throw new CustomException("User with this id not found");
        }
        userRepository.delete(userOptional.get());
        Map<String,Boolean> response = new HashMap<>();
        response.put("Deleted",Boolean.TRUE);
        return response;
    }

}

