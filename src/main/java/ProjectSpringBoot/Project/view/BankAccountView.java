package ProjectSpringBoot.Project.view;

import ProjectSpringBoot.Project.common.Currency;
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
public class BankAccountView {

    private Integer id;
    private String iban;
    private Currency currency;
    private BigDecimal balance;
}
