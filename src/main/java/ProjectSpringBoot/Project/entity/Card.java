package ProjectSpringBoot.Project.entity;

import ProjectSpringBoot.Project.common.CardType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "card_holder")
    private String cardHolder;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type")
    private CardType creditCardType;

    @Column(name = "card_number")
    private String cardNumber;

    @OneToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

}
