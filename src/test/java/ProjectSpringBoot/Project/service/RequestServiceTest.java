package ProjectSpringBoot.Project.service;

import ProjectSpringBoot.Project.common.Currency;
import ProjectSpringBoot.Project.common.Roles;
import ProjectSpringBoot.Project.common.Status;
import ProjectSpringBoot.Project.controller.requests.RequestRequest;
import ProjectSpringBoot.Project.entity.BankAccount;
import ProjectSpringBoot.Project.entity.Request;
import ProjectSpringBoot.Project.entity.User;
import ProjectSpringBoot.Project.mapper.RequestMapper;
import ProjectSpringBoot.Project.repo.BankAccountRepository;
import ProjectSpringBoot.Project.repo.RequestRepository;
import ProjectSpringBoot.Project.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RequestServiceTest {
    @Autowired
    private RequestService requestService;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BankAccountRepository accountRepository;
    private BankAccount account;
    @Autowired
    private RequestMapper requestMapper;
    private Integer requestId;

    @BeforeEach
    public void setUp() {
        {

            var user = User.builder()
                    .username("admin")
                    .password("admin")
                    .roles(Roles.CLIENT)
                    .build();
            userRepository.save(user);

            var account2 = BankAccount.builder()
                    .iban("b2ffs")
                    .userId(user)
                    .currency(Currency.EURO)
                    .balance(new BigDecimal("1234.4"))
                    .build();
            account = accountRepository.save(account2);

            var account = BankAccount.builder()
                    .iban("b2ffs")
                    .userId(user)
                    .currency(Currency.EURO)
                    .balance(new BigDecimal("1234.4"))
                    .build();
            accountRepository.save(account);

            var account3 = BankAccount.builder()
                    .iban("G1110")
                    .userId(user)
                    .currency(Currency.DOLLAR)
                    .balance(new BigDecimal("1000.4"))
                    .build();
            accountRepository.save(account3);

            var request = Request.builder()
                    .bankAccount(account)
                    .salary(new BigDecimal("1200000000"))
                    .status(Status.PENDING)
                    .build();
            requestId = requestRepository.save(request).getId();

            var request2 = Request.builder()
                    .bankAccount(account3)
                    .salary(new BigDecimal("111111111"))
                    .status(Status.PENDING)
                    .build();
            requestRepository.save(request2);
            var request3 = Request.builder()
                    .bankAccount(account2)
                    .salary(new BigDecimal("1234.4"))
                    .status(Status.PENDING)
                    .feedback("Good")
                    .build();
            requestRepository.save(request3);

        }
    }

    @AfterEach
    public void cleanUp() {
        requestRepository.deleteAll();
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void find_all_requests() {
        var requests = requestService.findAllRequests();
        assertThat(requests).isNotNull();
        System.out.println(requests);
    }

    @Transactional
    @Test
    void update_request() {
        var newRequest = RequestRequest.builder()
                .id(requestId)
                .accepted(true)
                .bankAccountId(account.getBankAccId())
                .feedback("Very GOOD")
                .build();
        var request = requestService.update(newRequest);;
        assertThat(request).isNotNull();
        System.out.println(request);
    }

}