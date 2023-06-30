package UserQuizManagement.demoUserQuiz.Entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;

import javax.persistence.*;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "topics")
@Getter
@Setter
@NoArgsConstructor
public class Topic {
    @Id
    @SequenceGenerator(name = "topic_sequence", sequenceName = "topic_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "topic_sequence")
    private Long topicId ;

    @Column(unique = true)
    private String topicName ;

    private String topicDescription ;

    @Transient
    private final Long noOfQuestions = 25L;

    @Transient
    private final Duration timeDuration = Duration.ofHours(1) ;


    @OneToMany( cascade = CascadeType.ALL,mappedBy = "topic", fetch = FetchType.LAZY )
    @JsonManagedReference
    private List<Score> topicScores ;

    public Topic(String topicName) {
        this.topicName = topicName;
    }

    public Topic(Long topicId, String topicName, String topicDescription) {
        this.topicId = topicId;
        this.topicName = topicName;
        this.topicDescription = topicDescription;
    }

    public Topic(Long topicId, String topicName) {
        this.topicId = topicId;
        this.topicName = topicName;
    }

    public Topic(String topicName, String topicDescription) {
        this.topicName = topicName;
        this.topicDescription = topicDescription;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "topicId=" + topicId +
                ", topicName='" + topicName + '\'' +
                ", topicDescription='" + topicDescription + '\'' +
                ", noOfQuestions=" + noOfQuestions +
                ", timeDuration=" + timeDuration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return topicId.equals(topic.topicId) && topicName.equals(topic.topicName) && topicDescription.equals(topic.topicDescription) && noOfQuestions.equals(topic.noOfQuestions) && timeDuration.equals(topic.timeDuration) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(topicId, topicName, topicDescription, noOfQuestions, timeDuration);
    }
}
