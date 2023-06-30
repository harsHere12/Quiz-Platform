package UserQuizManagement.demoUserQuiz.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Data
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode


public class TopicScoreDTO extends DTO {


    private Long topicId ;
    private String topicName ;
    private List<ScoreUserDTO> topicScores ;


}
