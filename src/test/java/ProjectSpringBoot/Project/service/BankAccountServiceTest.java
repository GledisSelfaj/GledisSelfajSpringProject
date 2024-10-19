package ProjectSpringBoot.Project.service;


import ProjectSpringBoot.Project.controller.requests.BankAccountRequest;
import ProjectSpringBoot.Project.entity.BankAccount;
import ProjectSpringBoot.Project.entity.User;
import ProjectSpringBoot.Project.repo.BankAccountRepository;
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

@SpringBootTest
public class BankAccountServiceTest {
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private BankerService bankerService;
    @Autowired
    private UserRepository userRepository;
    private Integer bankAccountId;
    private Integer bankAccountId2;
    private Integer bankAccountId3;
    @Autowired
    private IbanService ibanService;
    private User user;
    @BeforeEach
    public void setUp() {
        var user = User.builder()
                .username("gled")
                .password("1gg")
                .roles(Roles.CLIENT)
                .build();
        this.user = userRepository.save(user);


        var bankAccount = BankAccount.builder()
                .iban(ibanService.generateIban("1g","22211g"))
                .currency(Currency.EURO)
                .balance(new BigDecimal("111111.3"))
                .userId(user)
                .build();
        bankAccountId=bankAccountRepository.save(bankAccount).getBankAccId();

        var bankAccount2 = BankAccount.builder()
                .iban(ibanService.generateIban("1g","11dzm1n"))
                .currency(Currency.EURO)
                .balance(new BigDecimal("111111.3"))
                .userId(user)
                .build();
        bankAccountId2 = bankAccountRepository.save(bankAccount2).getBankAccId();

        var bankAccount3 = BankAccount.builder()
                .iban("11sdf4")
                .currency(Currency.EURO)
                .balance(new BigDecimal("111111.3"))
                .userId(user)
                .build();
        bankAccountId3=bankAccountRepository.save(bankAccount3).getBankAccId();
    }

    @AfterEach
    public void cleanUp() {
        bankAccountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testSave() {
        bankAccountRepository.findById(bankAccountId).get();

        bankAccountRepository.findById(bankAccountId2).get();

        bankAccountRepository.findById(bankAccountId3).get();

    }
    @Test
    void findall(){
        var listAccount=bankerService.findAllBankAccount();
        assertThat(listAccount.isEmpty()).isFalse();
        System.out.println(listAccount);
    }
    @Test
    void create_bank_account(){
        var accountRequest = BankAccountRequest.builder()
                .currency(Currency.EURO)
                .balance(new BigDecimal("111111.3"))
                .userId(user.getId())
                .bban("gls")
                .countryCode("tp")
                .build();
        var newBankAccount= bankAccountService.createBankAccount(accountRequest);
        assertThat(newBankAccount).isNotNull();
        assertThat(newBankAccount.getCurrency().equals(Currency.EURO));
    }

}
