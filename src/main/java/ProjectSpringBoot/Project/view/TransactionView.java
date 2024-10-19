package ProjectSpringBoot.Project.view;

import ProjectSpringBoot.Project.common.Currency;
import ProjectSpringBoot.Project.common.TransactionType;
import ProjectSpringBoot.Project.entity.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionView {
    private Integer id;
    private BigDecimal amount;
    private Currency currency;
    private TransactionType transactionType;
    private BankAccount bankAccount;
}
