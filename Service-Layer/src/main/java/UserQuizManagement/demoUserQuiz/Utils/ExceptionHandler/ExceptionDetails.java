package UserQuizManagement.demoUserQuiz.Utils.ExceptionHandler;


import java.time.LocalDateTime;

public class ExceptionDetails {

    private LocalDateTime timeStamp ;
    private String exceptionName ;
    private String message ;
    private String details ;



    public ExceptionDetails() {
    }

    public ExceptionDetails(String exceptionName,LocalDateTime timeStamp, String message, String details) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.details = details;
        this.exceptionName = exceptionName;
    }

    public ExceptionDetails(LocalDateTime timeStamp, String message, String details) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.details = details;
    }

    public String getException() {
        return exceptionName;
    }

    public void setException(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}

