package ProjectSpringBoot.Project.service;

import ProjectSpringBoot.Project.common.CardType;
import ProjectSpringBoot.Project.common.Currency;
import ProjectSpringBoot.Project.common.Roles;
import ProjectSpringBoot.Project.common.TransactionType;
import ProjectSpringBoot.Project.controller.requests.TransactionRequest;
import ProjectSpringBoot.Project.entity.BankAccount;
import ProjectSpringBoot.Project.entity.Card;
import ProjectSpringBoot.Project.entity.Transaction;
import ProjectSpringBoot.Project.entity.User;
import ProjectSpringBoot.Project.repo.BankAccountRepository;
import ProjectSpringBoot.Project.repo.CardRepository;
import ProjectSpringBoot.Project.repo.TransactionRepository;
import ProjectSpringBoot.Project.repo.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BankAccountRepository accountRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionRepository transactionRepository;
    private Integer bankAccountId;


    @BeforeEach
    public void setUp() {
        var user = User.builder()
                .username("admin")
                .password("admin")
                .roles(Roles.CLIENT)
                .build();
        userRepository.save(user);


        var bankAccount = BankAccount.builder()
                .iban("11sdf4")
                .currency(Currency.EURO)
                .balance(new BigDecimal("1110.3"))
                .userId(user)
                .build();
        bankAccountId = accountRepository.save(bankAccount).getBankAccId();

        var card = Card.builder()
                .cardNumber("1234")
                .cardHolder("Gled")
                .creditCardType(CardType.DEBIT)
                .bankAccount(bankAccount)
                .build();
        cardRepository.save(card);

        var bankAccount2 = BankAccount.builder()
                .iban("j0N1Da")
                .currency(Currency.EURO)
                .balance(new BigDecimal("1110.3"))
                .userId(user)
                .build();
        accountRepository.save(bankAccount2);
        var transaction = Transaction.builder()
                .transactionType(TransactionType.DEBIT)
                .bankAccount(bankAccount)
                .amount(new BigDecimal("1110.3"))
                .currency(Currency.EURO)
                .build();
        transactionRepository.save(transaction);
        var transaction2 = Transaction.builder()
                .transactionType(TransactionType.CREDIT)
                .bankAccount(bankAccount2)
                .amount(new BigDecimal("1110.3"))
                .currency(Currency.EURO)
                .build();
        transactionRepository.save(transaction2);
    }

    @AfterEach
    public void cleanUp() {
        transactionRepository.deleteAll();
        cardRepository.deleteAll();
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void transferMoney() {
        var transactionRequest = TransactionRequest.builder()
                .receiverIban("j0N1Da")
                .amount(new BigDecimal("200"))
                .senderBankAccountId(bankAccountId)
                .build();

        transactionService.transaction(transactionRequest);
    }

    @Test
    void get_all_transactions(){
        var transactionList = transactionService.findAllTransactions();
        assertThat(transactionList).isNotEmpty();
    }
    @Test
    void get_transactions_by_BankAccount(){
        var list = transactionService.getTransactionsByBankAccountId(bankAccountId);
        assertThat(list).isNotEmpty();
    }
}
























