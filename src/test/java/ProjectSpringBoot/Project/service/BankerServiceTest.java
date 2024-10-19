package ProjectSpringBoot.Project.service;

import ProjectSpringBoot.Project.common.Currency;
import ProjectSpringBoot.Project.common.Roles;
import ProjectSpringBoot.Project.common.Status;
import ProjectSpringBoot.Project.controller.requests.RequestRequest;
import ProjectSpringBoot.Project.controller.requests.UserRequest;
import ProjectSpringBoot.Project.entity.BankAccount;
import ProjectSpringBoot.Project.entity.Request;
import ProjectSpringBoot.Project.entity.User;
import ProjectSpringBoot.Project.repo.BankAccountRepository;
import ProjectSpringBoot.Project.repo.RequestRepository;
import ProjectSpringBoot.Project.repo.UserRepository;
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
public class BankerServiceTest {
    @Autowired
    private BankerService bankerService;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private BankAccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    private BankAccount account;
    private BankAccount bankAccount;
    private BankAccount bankAccount2;
    private Integer requestId;

    @BeforeEach
    void setUp() {
        var user = User.builder()
                .username("admin")
                .password("admin")
                .roles(Roles.CLIENT)
                .build();
        userRepository.save(user);

        var account = BankAccount.builder()
                .iban("b2ffs")
                .userId(user)
                .currency(Currency.EURO)
                .balance(new BigDecimal("1234.4"))
                .build();
        this.account = accountRepository.save(account);

        var bankAccount = BankAccount.builder()
                .iban("b2ffs")
                .userId(user)
                .currency(Currency.EURO)
                .balance(new BigDecimal("1234.4"))
                .build();
        this.bankAccount = accountRepository.save(bankAccount);

        var account3 = BankAccount.builder()
                .iban("G1110")
                .userId(user)
                .currency(Currency.DOLLAR)
                .balance(new BigDecimal("1000.4"))
                .build();
        bankAccount2 = accountRepository.save(account3);

        var request1 = Request.builder()
                .status(Status.PENDING)
                .bankAccount(account)
                .salary(new BigDecimal("790"))
                .build();
        requestId =requestRepository.save(request1).getId();


        var request2 = Request.builder()
                .bankAccount(bankAccount)
                .salary(new BigDecimal("19234.4"))
                .status(Status.PENDING)
                .build();
        requestRepository.save(request2);
        var request3 = Request.builder()
                .bankAccount(bankAccount2)
                .salary(new BigDecimal("12634.4"))
                .status(Status.ACCEPTED)
                .build();
        requestRepository.save(request3);


    }

    @AfterEach
    public void cleanUp() {

        requestRepository.deleteAll();
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void update_requests() {
        var newRequest1 = RequestRequest.builder()
                .id(requestId)
                .bankAccountId(account.getBankAccId())
                .salary(new BigDecimal("1000.4"))
                .feedback("VERY GOOD")
                .build();
        var request = bankerService.update(newRequest1);
        System.out.println(request);
    }

    @Test
    void get_requests() {
        var list = bankerService.findAllRequests();
        assertThat(list).isNotEmpty();
        list.forEach(System.out::println);
    }

    @Test
    void get_all_transactions() {
        var transactionList = bankerService.findAllTransactions();
        System.out.println(transactionList);
    }

    @Test
    void create_Client() {
        var userRequest = UserRequest.builder()
                .username("TEPELEN")
                .password("SHQIPERIA11")
                .roles(Roles.BANKER)
                .build();
        var newClient = bankerService.createClient(userRequest);
        assertThat(newClient).isNotNull();
        assertThat(newClient.getUsername()).isEqualTo(userRequest.getUsername());
        System.out.println(newClient);
    }

    @Test
    void find_all() {
        var listAccount = bankerService.findAllBankAccount();
        assertThat(listAccount.isEmpty()).isFalse();
        System.out.println(listAccount);
    }
}
