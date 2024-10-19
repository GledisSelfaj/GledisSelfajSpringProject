package ProjectSpringBoot.Project.exception;

public class SubtractedAmountIsGreaterThanBalanceException extends RuntimeException {
    public String message;


    public SubtractedAmountIsGreaterThanBalanceException(String message) {
        super(message);
        this.message = message;
    }
}