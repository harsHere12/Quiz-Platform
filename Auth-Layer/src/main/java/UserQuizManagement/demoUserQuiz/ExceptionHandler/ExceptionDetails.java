package UserQuizManagement.demoUserQuiz.ExceptionHandler;

import lombok.*;

import java.time.LocalDateTime;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class ExceptionDetails {
    private LocalDateTime timeStamp ;
    private String exceptionName ;
    private String message ;
    private String details ;

}
