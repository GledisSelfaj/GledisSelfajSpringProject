package ProjectSpringBoot.Project.controller.requests;

import ProjectSpringBoot.Project.common.Currency;
import ProjectSpringBoot.Project.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountRequest {
    private Integer BankAccId;
    private String iban;
    private String bban;
    private String countryCode;
    private Currency currency;
    private BigDecimal balance;
    private Integer userId;
    private BigDecimal salary;
}
