package ProjectSpringBoot.Project.entity;

import ProjectSpringBoot.Project.common.Status;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "feedback")
    private String feedback;

    @OneToOne
    @JoinColumn(name ="bank_account_id")
    private BankAccount bankAccount;
}
