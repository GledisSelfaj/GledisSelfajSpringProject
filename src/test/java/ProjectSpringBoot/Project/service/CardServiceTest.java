package ProjectSpringBoot.Project.service;


import ProjectSpringBoot.Project.common.CardType;
import ProjectSpringBoot.Project.entity.BankAccount;
import ProjectSpringBoot.Project.entity.Card;
import ProjectSpringBoot.Project.entity.User;
import ProjectSpringBoot.Project.exception.CardHasBankAccountException;
import ProjectSpringBoot.Project.exception.NoBankAccountFoundException;
import ProjectSpringBoot.Project.repo.BankAccountRepository;
import ProjectSpringBoot.Project.repo.CardRepository;
import ProjectSpringBoot.Project.repo.UserRepository;
import ProjectSpringBoot.Project.common.Currency;
import ProjectSpringBoot.Project.common.Roles;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
public class CardServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BankAccountRepository accountRepository;
    @Autowired
    private CardService cardService;
    private User user;
    private Integer bankAccountID;
    @Autowired
    private CardRepository cardRepository;

    @BeforeEach
    public void setUp() {
        var user = User.builder()
                .username("admin")
                .password("admin")
                .roles(Roles.CLIENT)
                .build();
        this.user = userRepository.save(user);

    }


    @Test
    void save_card_for_a_bank_account() {
        var bankAccount = BankAccount.builder()
                .iban("11sdf4")
                .currency(Currency.EURO)
                .balance(new BigDecimal("111111.3"))
                .userId(this.user)
                .build();
        bankAccountID = accountRepository.save(bankAccount).getBankAccId();
        var card = cardService.createDebitCard(bankAccountID);
        assertThat(card).isNotNull();
        System.out.println(card);
    }

    @Test
    void get_card_by_UserId() {
        var account = BankAccount.builder()
                .iban("11sdf4")
                .currency(Currency.DOLLAR)
                .balance(new BigDecimal("111111.3"))
                .userId(this.user)
                .build();
        accountRepository.save(account);
        var card = Card.builder()
                .cardNumber("a5aas11")
                .bankAccount(account)
                .cardHolder("gled")
                .creditCardType(CardType.DEBIT)
                .build();
        cardRepository.save(card);
        var bankAccount = BankAccount.builder()
                .iban("bl112gjd")
                .currency(Currency.DOLLAR)
                .balance(new BigDecimal("121889.3"))
                .userId(this.user)
                .build();
        accountRepository.save(bankAccount);
        var newCard = Card.builder()
                .cardNumber("a65sss")
                .bankAccount(bankAccount)
                .cardHolder("gled")
                .creditCardType(CardType.DEBIT)
                .build();
        cardRepository.save(newCard);
        var cards = cardService.getCardByUserId(user.getId());
        assertThat(cards).isNotNull();
        System.out.println(cards);
    }
}
