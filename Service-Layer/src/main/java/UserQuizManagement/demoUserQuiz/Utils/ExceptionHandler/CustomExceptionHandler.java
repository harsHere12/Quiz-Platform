package UserQuizManagement.demoUserQuiz.Utils.ExceptionHandler;

import UserQuizManagement.demoUserQuiz.Utils.Exceptions.NotFound;
import UserQuizManagement.demoUserQuiz.Utils.Exceptions.UnauthorizedUser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;


@ControllerAdvice
class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) throws Exception {
        ExceptionDetails exceptionDetails = new ExceptionDetails(ex.getClass().getName(),LocalDateTime.now(),ex.getMessage(), request.getDescription(false)) ;
        return new ResponseEntity(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR) ;
    }
    @ExceptionHandler(NotFound.class)
    public final ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request) throws Exception {
        ExceptionDetails exceptionDetails = new ExceptionDetails(ex.getClass().getName(),LocalDateTime.now(),ex.getMessage(), request.getDescription(false)) ;
        return new ResponseEntity(exceptionDetails, HttpStatus.NOT_FOUND) ;
    }
    @ExceptionHandler(IllegalAccessException.class)
    public final ResponseEntity<Object> handleEmailAlreadyTakenException(Exception ex, WebRequest request) throws Exception {
        ExceptionDetails exceptionDetails = new ExceptionDetails(ex.getClass().getName(),LocalDateTime.now(),ex.getMessage(), request.getDescription(false)) ;
        return new ResponseEntity(exceptionDetails, HttpStatus.BAD_REQUEST) ;

    }
    @ExceptionHandler(UnauthorizedUser.class)
    public final ResponseEntity<Object> handleUnauthorizedUserException(Exception ex, WebRequest request) throws Exception {
        ExceptionDetails exceptionDetails = new ExceptionDetails(ex.getClass().getName(),LocalDateTime.now(),ex.getMessage(), request.getDescription(false)) ;
        return new ResponseEntity(exceptionDetails, HttpStatus.FORBIDDEN) ;
    }
//
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(),ex.getMessage(), request.getDescription(false)) ;
//        return new ResponseEntity(exceptionDetails, HttpStatus.BAD_REQUEST) ;
//    }


}
