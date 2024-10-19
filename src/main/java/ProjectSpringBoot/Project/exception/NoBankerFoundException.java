package ProjectSpringBoot.Project.exception;

public class NoBankerFoundException extends RuntimeException {
    public String message;


    public NoBankerFoundException(String message) {
        super(message);
        this.message = message;
    }
}