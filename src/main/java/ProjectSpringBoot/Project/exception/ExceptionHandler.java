package ProjectSpringBoot.Project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(CardHasBankAccountException.class)
    public ResponseEntity<String> handleRuntimeException(CardHasBankAccountException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserNameExistException.class)
    public ResponseEntity<String> handleRuntimeException(UserNameExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoBankAccountFoundException.class)
    public ResponseEntity<String> handleRuntimeException(NoBankAccountFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoCardFoundException.class)
    public ResponseEntity<String> handleNoCardFoundException(NoCardFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoRequestFoundException.class)
    public ResponseEntity<String> handleNoRequestFoundException(NoRequestFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(NoUserFoundException.class)
    public ResponseEntity<String> handleNoUserFoundException(NoUserFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(SubtractedAmountIsGreaterThanBalanceException.class)
    public ResponseEntity<String> handleSubtractedAmountIsGreaterThanBalanceException(SubtractedAmountIsGreaterThanBalanceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(NoBankerFoundException.class)
    public ResponseEntity<String> handleNoBankerFoundException(NoBankerFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(NoClientFoundException.class)
    public ResponseEntity<String> handleNoClientFoundException(NoClientFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
