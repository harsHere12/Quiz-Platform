package UserQuizManagement.demoUserQuiz.Utils.Exceptions;

public class UnauthorizedUser extends IllegalAccessException{
    public UnauthorizedUser(String s) {
        super(s);
    }
}
