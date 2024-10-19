package ProjectSpringBoot.Project.view;

import ProjectSpringBoot.Project.common.CardType;
import ProjectSpringBoot.Project.entity.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CardView {
    private Integer id;
    private String cardHolder;
    private CardType creditCardType;
    private String cardNumber;
    private BankAccount bankAccount;
}
