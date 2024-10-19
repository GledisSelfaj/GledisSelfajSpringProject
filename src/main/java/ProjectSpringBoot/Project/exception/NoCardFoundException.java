package ProjectSpringBoot.Project.exception;

public class NoCardFoundException extends RuntimeException {
    public String message;


    public NoCardFoundException(String message) {
        super(message);
        this.message = message;
    }
}