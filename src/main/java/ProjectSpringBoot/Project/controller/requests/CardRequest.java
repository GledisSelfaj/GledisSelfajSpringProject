package ProjectSpringBoot.Project.controller.requests;

import ProjectSpringBoot.Project.common.CardType;
import ProjectSpringBoot.Project.entity.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardRequest {
    private Integer id;
    private String cardHolder;
    private CardType creditCardType;
    private String cardNumber;
    private BankAccount bankAccount;
}
