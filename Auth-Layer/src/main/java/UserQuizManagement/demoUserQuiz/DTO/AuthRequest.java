package UserQuizManagement.demoUserQuiz.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class AuthRequest {
    private String userEmail;
    private String userPassword;
}
