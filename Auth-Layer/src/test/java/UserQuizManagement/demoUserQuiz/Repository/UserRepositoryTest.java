package UserQuizManagement.demoUserQuiz.Repository;

import UserQuizManagement.demoUserQuiz.Entity.Users;
import UserQuizManagement.demoUserQuiz.ExceptionHandler.Exceptions.NotFound;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@RunWith(SpringRunner.class)
class UserRepositoryTest {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;
  @Test
    void existsByUserEmail() throws ParseException {


        //given
        String Password = "champak";
        String encodedPassword = passwordEncoder.encode(Password);
        Users user = new Users(Long.valueOf(4),"Champak","8765432109","champaklal@gmail.com",encodedPassword,new Date(),"user","what's your favourite food","fafda");
        //when
        userRepository.save(user);
        boolean flag = userRepository.existsByUserEmail(user.getUserEmail());
        //then
        assertThat(flag).isTrue();
    }

//    @Test
//    void findByRoles() {
//        String password = "Tipendra";
//        String encodedPassword = passwordEncoder.encode(password);
//        Users user = new Users(Long.valueOf(3),"Tipendra","9876867852","tipendragada@gadaelectronics.com","encodedPassword",new Date(),"user","what's your crush name?","Babita ji");
//        userRepository.save(user);
//        Optional<Users> usersOptional = userRepository.findByRoles("tipendragada@gadaelectronics.com");
//        String userRole = usersOptional.get().getroles();
//
//        assertThat(userRole).isEqualTo(user.getroles());
//
//    }

    @Test
    void findByUserEmail() throws NotFound {
      String password = "jethalal";
      String encodedPassword = passwordEncoder.encode(password);
      Users user = new Users(Long.valueOf(5),"jethalal","9876867859","jethalalgada@gadaelectronics.com",encodedPassword,new Date(),"user","what's your crush name?","Babita ji");
      userRepository.save(user);
        Optional<Users> usersOptional = userRepository.findByUserEmail("jethalalgada@gadaelectronics.com");
        boolean emailFound = usersOptional.isPresent();

        assertThat(emailFound).isTrue();
    }


    @AfterEach
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }
}