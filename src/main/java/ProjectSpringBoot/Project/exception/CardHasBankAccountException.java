package ProjectSpringBoot.Project.exception;

public class CardHasBankAccountException extends RuntimeException {
    public String message;


    public CardHasBankAccountException(String message) {
        super(message);
        this.message = message;
    }
}