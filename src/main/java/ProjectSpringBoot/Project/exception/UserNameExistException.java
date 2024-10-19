package ProjectSpringBoot.Project.exception;

public class UserNameExistException extends RuntimeException {
    public String message;


    public UserNameExistException(String message) {
        super(message);
        this.message = message;
    }
}