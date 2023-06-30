package UserQuizManagement.demoUserQuiz.Entity;


import UserQuizManagement.demoUserQuiz.DTO.TopicDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.Objects;

@Entity
@Table(name ="scores")
//@IdClass(ScoreId.class)
@NoArgsConstructor

@Getter
@Setter


public class Score {
    @Id
    @SequenceGenerator(name = "score_sequence",sequenceName = "score_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "score_sequence")
    private Long scoreId ;

    @Max(value = 100)
    private Long score ;

//
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name   = "topic_id" ,  nullable = false)
    @JsonBackReference
    private Topic topic  ;

    @ManyToOne(fetch =  FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id" , nullable = false)
    private User user ;

    public Score(Long score, Topic topic) {
        this.score = score;
        this.topic = topic;
    }

    public Score(Long score, User user , Topic topic) {
        this.score = score;
        this.topic = topic;
        this.user = user;
    }

    public Score(Long score, User user) {
        this.score = score;
        this.user = user;
    }

    public Score(Long scoreId, Long score, Topic topic) {
        this.scoreId = scoreId;
        this.score = score;
        this.topic = topic;
    }

    public Score(Long scoreId, Long score, Topic topic, User user) {
        this.scoreId = scoreId;
        this.score = score;
        this.topic = topic;
        this.user = user;
    }

    public Long getScoreId() {
        return scoreId;
    }

    public Topic getTopic() {
        return topic;
    }

    public User getUser() {
        return user ;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Score{" +
                "scoreId=" + scoreId +
                ", score=" + score +
//                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score1 = (Score) o;
        return ( Objects.equals(scoreId, score1.scoreId) || (Objects.equals(user.getUserId(),score1.user.getUserId()) && (Objects.equals(topic.getTopicId(),score1.getTopic().getTopicId()))) && Objects.equals(score, score1.score) )  ;
    }
//&& Objects.equals(topic, score1.topic) && Objects.equals(user, score1.user)
    @Override
    public int hashCode() {
        return Objects.hash(scoreId, score, topic, user);
    }
}
