package ProjectSpringBoot.Project.service;

import ProjectSpringBoot.Project.common.CardType;
import ProjectSpringBoot.Project.common.Currency;
import ProjectSpringBoot.Project.common.Roles;
import ProjectSpringBoot.Project.common.Status;
import ProjectSpringBoot.Project.common.TransactionType;
import ProjectSpringBoot.Project.controller.requests.BankAccountRequest;
import ProjectSpringBoot.Project.entity.BankAccount;
import ProjectSpringBoot.Project.entity.Card;
import ProjectSpringBoot.Project.entity.Request;
import ProjectSpringBoot.Project.entity.Transaction;
import ProjectSpringBoot.Project.entity.User;
import ProjectSpringBoot.Project.repo.BankAccountRepository;
import ProjectSpringBoot.Project.repo.CardRepository;
import ProjectSpringBoot.Project.repo.RequestRepository;
import ProjectSpringBoot.Project.repo.TransactionRepository;
import ProjectSpringBoot.Project.repo.UserRepository;
import ProjectSpringBoot.Project.service.impl.CardNumberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
@SpringBootTest
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;
    @Autowired
    private BankAccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private CardNumberServiceImpl cardNumberService;
    private Integer bankaccount;
    private BankAccount bankaccount2;
    private User user;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private TransactionRepository transactionRepository;



    @BeforeEach
    void setup() {
        var user = User.builder()
                .username("admin")
                .password("admin")
                .roles(Roles.CLIENT)
                .build();
        this.user = userRepository.save(user);

    }

    @AfterEach
    public void cleanUp() {
        requestRepository.deleteAll();
        transactionRepository.deleteAll();
        cardRepository.deleteAll();
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void create_request() {
        var account1 = BankAccount.builder()
                .iban("b2ffs")
                .userId(user)
                .currency(Currency.EURO)
                .balance(new BigDecimal("1234.4"))
                .build();
        bankaccount = accountRepository.save(account1).getBankAccId();
        var request = BankAccountRequest.builder()
                .BankAccId(bankaccount)
                .iban("b2ffs")
                .userId(user.getId())
                .currency(Currency.DOLLAR)
                .balance(new BigDecimal("1234.4"))
                .salary(new BigDecimal("600.0"))
                .build();
        var newRequest = clientService.createRequest(request);
        assertThat(newRequest).isNotNull();
        System.out.println(newRequest);
    }

    @Test
    void get_request() {
        var account1 = BankAccount.builder()
                .iban("b2ffs")
                .userId(user)
                .currency(Currency.EURO)
                .balance(new BigDecimal("1234.4"))
                .build();
         accountRepository.save(account1);
        var card1 = Card.builder()
                .cardHolder("Gled")
                .cardNumber(cardNumberService.getCardNumber())
                .creditCardType(CardType.DEBIT)
                .bankAccount(account1)
                .build();
        cardRepository.save(card1);

        var request = Request.builder()
                .bankAccount(account1)
                .status(Status.ACCEPTED)
                .feedback("Good")
                .salary(new BigDecimal("111111"))
                .build();
        requestRepository.save(request);
        var requests = clientService.getRequestByUserId(user.getId());
        assertThat(requests.get(0).getStatus()).isEqualTo(Status.ACCEPTED);
        System.out.println(requests.get(0));
    }

    @Test
    void get_bank_account_by_id() {

        var bankAccount2 = BankAccount.builder()
                .iban("b2ffs")
                .userId(user)
                .currency(Currency.EURO)
                .balance(new BigDecimal("1234.4"))
                .build();
        this.bankaccount2 = accountRepository.save(bankAccount2);
        var account1 = BankAccount.builder()
                .iban("b2ffs")
                .userId(user)
                .currency(Currency.EURO)
                .balance(new BigDecimal("1234.4"))
                .build();
        accountRepository.save(account1);

        var account = clientService.getBankAccountByUserId(user.getId());
        assertThat(account).isNotNull();
        System.out.println(account);
    }

    @Test
    void get_card_by_userId() {
        var bankAccount2 = BankAccount.builder()
                .iban("b2ffs")
                .userId(user)
                .currency(Currency.EURO)
                .balance(new BigDecimal("1234.4"))
                .build();
        this.bankaccount2 = accountRepository.save(bankAccount2);
        var card = Card.builder()
                .cardHolder("Gled")
                .cardNumber(cardNumberService.getCardNumber())
                .creditCardType(CardType.DEBIT)
                .bankAccount(bankAccount2)
                .build();
        cardRepository.save(card);
        var cardCreate = clientService.getCardByUserId(user.getId());
        assertThat(cardCreate).isNotNull();
        System.out.println(cardCreate);
    }

    @Test
    void get_transaction_by_Bank_AccountId() {
        var account1 = BankAccount.builder()
                .iban("b2ffs")
                .userId(user)
                .currency(Currency.EURO)
                .balance(new BigDecimal("1234.4"))
                .build();
        accountRepository.save(account1);
        var transaction2 = Transaction.builder()
                .bankAccount(account1)
                .transactionType(TransactionType.CREDIT)
                .amount(new BigDecimal("1905000"))
                .currency(Currency.DOLLAR)
                .build();
      transactionRepository.save(transaction2);
        var transaction = clientService.getTransactionsByBankAccountId(account1.getBankAccId());
        assertThat(transaction).isNotNull();
        System.out.println(transaction);
    }

    @Test
    void card_create() {
        var bankAccount2 = BankAccount.builder()
                .iban("b2ffs")
                .userId(user)
                .currency(Currency.EURO)
                .balance(new BigDecimal("1234.4"))
                .build();
        this.bankaccount2 = accountRepository.save(bankAccount2);
        var card = clientService.createDebitCard(bankaccount2.getBankAccId());
        assertThat(card).isNotNull();
        System.out.println(card);
    }

}
