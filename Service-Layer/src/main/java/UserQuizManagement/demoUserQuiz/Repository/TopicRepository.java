package UserQuizManagement.demoUserQuiz.Repository;

import UserQuizManagement.demoUserQuiz.Entity.Score;
import UserQuizManagement.demoUserQuiz.Entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Long> {
//
//    @Query("Select t From Topic t Join t.topicScores as s Join s.user u where t.topicId = ?1 ")
//    Optional<Topic> findAllScoresTOfTopic(Long topicId);
    @Query("Select t From Topic t ")
    List<Topic> findAllTopics();

    @Query("Select t From Topic t where topicId = ?1")
    Optional<Topic> findTopicById(Long topicId);

}
