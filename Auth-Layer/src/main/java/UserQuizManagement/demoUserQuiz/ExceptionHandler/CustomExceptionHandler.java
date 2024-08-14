package UserQuizManagement.demoUserQuiz.ExceptionHandler;

import UserQuizManagement.demoUserQuiz.ExceptionHandler.Exceptions.EmailAlreadyExists;
import UserQuizManagement.demoUserQuiz.ExceptionHandler.Exceptions.NotFound;
import UserQuizManagement.demoUserQuiz.ExceptionHandler.Exceptions.PasswordOrEmailDoesNotMatches;
import UserQuizManagement.demoUserQuiz.ExceptionHandler.Exceptions.YouAreNotAuthorised;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) throws Exception {
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), ex.getClass().getName(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(NotFound.class)
    public final ResponseEntity<Object> handleEmailException(Exception ex, WebRequest request) throws Exception {
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), ex.getClass().getName(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(EmailAlreadyExists.class)
    public final ResponseEntity<Object> handleEmailExisting(Exception ex, WebRequest request) throws Exception {
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), ex.getClass().getName(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(YouAreNotAuthorised.class)
    public final ResponseEntity<Object> handleAuthorisation(Exception ex, WebRequest request) throws Exception {
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), ex.getClass().getName(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionDetails, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(PasswordOrEmailDoesNotMatches.class)
    public final ResponseEntity<Object> handlePasswordNotMatching(Exception ex, WebRequest request) throws Exception {
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), ex.getClass().getName(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionDetails, HttpStatus.BAD_REQUEST);
    }
}
