package UserQuizManagement.demoUserQuiz.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class UpdateDTO {
    private String userName ;
    private String userPhoneNo;
    private String userEmail;
    private String userPassword;
    @JsonFormat(pattern = "dd/mm/yyyy")
    private Date dob;
    private String question;
    private String answer;

}
