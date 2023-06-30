package UserQuizManagement.demoUserQuiz.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class ForgotPasswordDto {
    private String userEmail;
    private String question;
    private String answer;
    private String newPassword;

}
