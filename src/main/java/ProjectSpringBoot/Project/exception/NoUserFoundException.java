package ProjectSpringBoot.Project.exception;

public class NoUserFoundException extends RuntimeException {
    public String message;


    public NoUserFoundException(String message) {
        super(message);
        this.message = message;
    }
}