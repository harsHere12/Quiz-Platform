package UserQuizManagement.demoUserQuiz.Service;

import UserQuizManagement.demoUserQuiz.CustomException;
import UserQuizManagement.demoUserQuiz.DTO.ForgotPasswordDto;
import UserQuizManagement.demoUserQuiz.DTO.UpdateDTO;
import UserQuizManagement.demoUserQuiz.Entity.Users;
import UserQuizManagement.demoUserQuiz.ExceptionHandler.Exceptions.EmailAlreadyExists;
import UserQuizManagement.demoUserQuiz.ExceptionHandler.Exceptions.NotFound;
import UserQuizManagement.demoUserQuiz.ExceptionHandler.Exceptions.YouAreNotAuthorised;
import UserQuizManagement.demoUserQuiz.Repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


//
//    @BeforeEach
//    void setUp() {
////       AutoCloseable closeable = MockitoAnnotations.openMocks(this);
//       userService = new UserService();
//    }
//
    @AfterEach
    void tearDown() {
        userRepository.deleteAll();

    }



    @Test
    void getUsers() throws ParseException, YouAreNotAuthorised {
        List<Users> expected = new ArrayList<>();
        Users user1 = new Users();
        user1.setUserEmail("shankar@gmail.com");
        user1.setUserPassword("shankar");
        user1.setUserPhoneNo("8788654321");
        user1.setroles("USER");
        String sDate1="31/12/1998";
        Date date1=new SimpleDateFormat("dd/MM/yyyy"). parse(sDate1);
        user1.setDob(date1);
        user1.setQuestion("what's your dog name?");
        user1.setAnswer("titu");
        userRepository.save(user1);
        expected.add(user1);
        Users user2 = new Users();
        user2.setUserEmail("shankara@gmail.com");
        user2.setUserPassword("shankara");
        user2.setUserPhoneNo("8788654311");
        user2.setroles("USER");
        String sDate2="31/12/1997";
        Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(sDate2);
        user2.setDob(date1);
        user2.setQuestion("what's your dog name?");
        user2.setAnswer("tituu");
        userRepository.save(user2);
        expected.add(user2);
        List<Users> actual = userService.getUsers();
//        System.out.println(actual.toArray().length);
        actual.toArray();
        Assert.assertEquals(actual.get(0).getUserEmail(), expected.get(0).getUserEmail());


    }

    @Test
    void getUserById() throws ParseException, NotFound {
     Users user1 = new Users();
        user1.setUserEmail("shankar@gmail.com");
        user1.setUserPassword("shankar");
        user1.setUserPhoneNo("8788654321");
        user1.setUserName("Eespiderman");
        user1.setroles("USER");
        String sDate1="31/12/1988";
        Date date1=new SimpleDateFormat("dd/MM/yyyy"). parse(sDate1);
        user1.setDob(date1);
        user1.setQuestion("what's your dog name?");
        user1.setAnswer("tuktuk");
        userRepository.save(user1);
        Users expected = user1;
        Users actual = userService.getUserById(user1.getUserId());
        assertEquals(expected.getUserId(),actual.getUserId());

    }

    @Test
    void createNewUser() throws Exception {
        Users user1 = new Users();
        user1.setUserEmail("shankar@gmail.com");
        user1.setUserPassword("shankar");
        user1.setUserPhoneNo("8788654321");
        user1.setUserName("Eespiderman");
        String sDate1="31/12/1988";
        Date date1=new SimpleDateFormat("dd/MM/yyyy"). parse(sDate1);
        user1.setDob(date1);
        user1.setQuestion("what's your dog name?");
        user1.setAnswer("tuktuk");
        Users actual = userService.createNewUser(user1);
        Users expected = user1;
        assertEquals(expected.getUserEmail(),actual.getUserEmail());

    }

    @Test
    void createNewAdmin() throws Exception {
        Users user1 = new Users();
        user1.setUserEmail("bhayankar@gmail.com");
        user1.setUserPassword("bhayankar");
        user1.setUserPhoneNo("8788654321");
        user1.setUserName("Bhayankar");
        user1.setroles("ADMIN");
        String sDate1="31/12/1988";
        Date date1=new SimpleDateFormat("dd/MM/yyyy"). parse(sDate1);
        user1.setDob(date1);
        user1.setQuestion("what's your pet name?");
        user1.setAnswer("khatarnak");
        Users actual = userService.createNewAdmin(user1);
        Users expected = user1;
        assertEquals(expected.getroles(),actual.getroles());
        assertEquals(expected.getUserEmail(),actual.getUserEmail());
    }

    @Test
    void forgotPassword() throws ParseException, CustomException, NotFound {
        Users  expected = new Users();
        expected.setUserEmail("bhayankar@gmail.com");
        expected.setUserPassword(passwordEncoder.encode("bhayankar"));
        expected.setUserPhoneNo("8788654321");
        expected.setUserName("Bhayankar");
        expected.setroles("ADMIN");
        String sDate1="31/12/1988";
        Date date1=new SimpleDateFormat("dd/MM/yyyy"). parse(sDate1);
        expected.setDob(date1);
        expected.setQuestion("what's your pet name?");
        expected.setAnswer("khatarnak");
        userRepository.save(expected);
        ForgotPasswordDto forgotPasswordDto1 = new ForgotPasswordDto(expected.getUserEmail(), expected.getQuestion(), expected.getAnswer(), "bhayankar1");
        Users actual = userService.forgotPassword(forgotPasswordDto1);
        String expectedPass = "bhayankar1";
        assertEquals(expected.getUserEmail(),actual.getUserEmail());
        assertThat(passwordEncoder.matches(expectedPass, actual.getUserPassword())).isTrue();



    }

    @Test
    void updateUser() throws ParseException, EmailAlreadyExists, CustomException, NotFound, EmailAlreadyExists {
        Users user1 = new Users();
        user1.setUserName("Raju");
        user1.setUserEmail("raju@gmail.com");
        user1.setUserPhoneNo("6514237327");
        String sDate1="31/12/1988";
        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        user1.setDob(date1);
        String sDate2="31/12/1988";
        Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(sDate2);
        user1.setQuestion("what is your name");
        user1.setAnswer("raju");
        userRepository.save(user1);
        Principal principal1 = new Principal() {
            @Override
            public String getName() {
                return user1.getUserEmail();        }
        };
        UpdateDTO updateDTO = new UpdateDTO("Farhan","1243243","farhan@gmail.com","hagdjhga",date2,"What is your father name","abba");
        Users actual = userService.updateUser(updateDTO,principal1);
        user1.setUserName("Farhan");
        user1.setUserEmail("farhan@gmail.com");
        user1.setUserPhoneNo("1243243");
        user1.setDob(date2);
        user1.setQuestion("what is your father name");
        user1.setAnswer("abba");
        assertEquals(user1.getUserEmail(),actual.getUserEmail());}


    void deleteUser() throws CustomException, ParseException {
        Users user1 = new Users();
        user1.setUserName("Raju");
        user1.setUserEmail("raju@gmail.com");
        user1.setUserPhoneNo("6514237327");
        String sDate1="31/12/1988";
        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        user1.setDob(date1);
        //        user1.setUserPassword(passwordEncoder.encode("raju"));
        user1.setQuestion("what is your name");
        user1.setAnswer("raju");
        userRepository.save(user1);
        Map<String,Boolean> expected = new HashMap<>();
        expected.put("Deleted", Boolean.TRUE);
        Map<String,Boolean> actual = userService.deleteUser(userRepository.findByUserEmail(user1.getUserEmail()).get().getUserId());
        assertEquals(expected.get("Deleted"),actual.get("Deleted"));    }
}