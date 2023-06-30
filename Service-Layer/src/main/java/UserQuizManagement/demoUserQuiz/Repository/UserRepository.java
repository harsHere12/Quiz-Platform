package UserQuizManagement.demoUserQuiz.Repository;

import UserQuizManagement.demoUserQuiz.Entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


//    @Query("Select u from User u Join u.userScores s Join s.topic t ")
//    public List<User> findAllUserScores();
}
