package UserQuizManagement.demoUserQuiz.Repository;



import UserQuizManagement.demoUserQuiz.Entity.Score;
import UserQuizManagement.demoUserQuiz.Utils.ScoreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, ScoreId> {

//    @Query("select s from Score s Inner Join s.topic t Order By s.score desc ")
//    List <Score> findAllScore() ;
    @Query("select s from Score s Join s.topic t Join s.user u where u.userId = ?1 Order By s.score desc")
    List <Score> findAllScoresById(Long userId);
    @Query("select s from Score s Join s.topic t Join s.user u where u.userId = ?1 and t.topicId = ?2" )
    List<Score> findScore(Long userId, Long topicId) ;


}
