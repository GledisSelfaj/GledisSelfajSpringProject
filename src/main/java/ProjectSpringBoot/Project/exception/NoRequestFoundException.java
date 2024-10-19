package ProjectSpringBoot.Project.exception;

public class NoRequestFoundException extends RuntimeException {
    public String message;


    public NoRequestFoundException(String message) {
        super(message);
        this.message = message;
    }
}