package UserQuizManagement.demoUserQuiz.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Data
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode

public class TopicDTO extends DTO{
    private Long topicId ;
    private String topicName ;
    private String topicDescription ;

}
