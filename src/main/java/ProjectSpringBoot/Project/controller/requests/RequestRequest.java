package ProjectSpringBoot.Project.controller.requests;

import ProjectSpringBoot.Project.common.Status;
import ProjectSpringBoot.Project.entity.BankAccount;
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
public class RequestRequest {

    private Integer id;
    private BigDecimal salary;
    private String feedback;
    private Integer bankAccountId;
    private boolean accepted;
}
