package ProjectSpringBoot.Project.exception;

public class NoClientFoundException extends RuntimeException {
    public String message;


    public NoClientFoundException(String message) {
        super(message);
        this.message = message;
    }
}