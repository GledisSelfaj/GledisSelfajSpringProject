package ProjectSpringBoot.Project.view;

import ProjectSpringBoot.Project.common.Status;
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
public class RequestView {

    private Integer id;
    private Status status;
    private BigDecimal salary;
    private String feedback;
    private BankAccount bankAccount;
}
