package ProjectSpringBoot.Project.exception;

public class NoBankAccountFoundException extends RuntimeException {
    public String message;


    public NoBankAccountFoundException(String message) {
        super(message);
        this.message = message;
    }
}