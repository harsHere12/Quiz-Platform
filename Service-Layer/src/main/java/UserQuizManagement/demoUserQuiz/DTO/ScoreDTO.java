package UserQuizManagement.demoUserQuiz.DTO;

import UserQuizManagement.demoUserQuiz.Entity.Topic;
import lombok.*;

import java.util.List;

//@Getter
//@Setter
//@AllArgsConstructor
//@RequiredArgsConstructor
//@ToString
//class Score {
//    private String topicName ;
//    private Long score ;
//}

@Getter
@Setter
@AllArgsConstructor
@Data
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
public class ScoreDTO extends DTO {
    private Long scoreId ;
    private Long score ;
    private TopicDTO topic ;
}
