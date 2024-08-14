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
public class UserScoreDTO extends DTO{
    private Long userId ;
    private String userName ;
    private List <ScoreDTO> userScores ;


}
