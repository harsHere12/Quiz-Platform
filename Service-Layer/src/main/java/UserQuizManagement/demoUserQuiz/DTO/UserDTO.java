package UserQuizManagement.demoUserQuiz.DTO;

import UserQuizManagement.demoUserQuiz.Entity.Score;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Data
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
public class UserDTO extends DTO {
    private Long userId ;
    private String userName ;

}
