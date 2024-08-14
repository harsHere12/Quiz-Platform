package UserQuizManagement.demoUserQuiz.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Data
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
public class ScoreUserDTO {
    private Long scoreId;

    private Long score;
    private UserDTO user;
}
