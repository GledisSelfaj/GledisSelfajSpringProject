package ProjectSpringBoot.Project.rest;

import ProjectSpringBoot.Project.common.Currency;
import ProjectSpringBoot.Project.common.Roles;
import ProjectSpringBoot.Project.common.Status;
import ProjectSpringBoot.Project.common.TransactionType;
import ProjectSpringBoot.Project.controller.requests.BankAccountRequest;
import ProjectSpringBoot.Project.controller.requests.RequestRequest;
import ProjectSpringBoot.Project.controller.requests.UserRequest;
import ProjectSpringBoot.Project.entity.BankAccount;
import ProjectSpringBoot.Project.entity.Request;
import ProjectSpringBoot.Project.entity.Transaction;
import ProjectSpringBoot.Project.entity.User;
import ProjectSpringBoot.Project.repo.BankAccountRepository;
import ProjectSpringBoot.Project.repo.CardRepository;
import ProjectSpringBoot.Project.repo.RequestRepository;
import ProjectSpringBoot.Project.repo.TransactionRepository;
import ProjectSpringBoot.Project.repo.UserRepository;
import ProjectSpringBoot.Project.service.UserService;
import ProjectSpringBoot.Project.view.BankAccountView;
import ProjectSpringBoot.Project.view.RequestView;
import ProjectSpringBoot.Project.view.TransactionView;
import ProjectSpringBoot.Project.view.UserView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@AutoConfigureMockMvc
public class BankerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    private User user;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    private BankAccount account;
    @Autowired
    private CardRepository cardRepository;
    private Transaction transaction;
    private Request request;
    private BankAccount bankAccount;
    @Autowired
    private UserService userService;
    @BeforeEach
    void setUp() {
        var banker = User.builder()
                .username("GLED")
                .password("{noop}123456")
                .roles(Roles.BANKER)
                .build();
        userRepository.save(banker);
        var newUser = User.builder()
                .username("JOHN")
                .password("{noop}12")
                .build();
        user = userRepository.save(newUser);

        var bankAccount = BankAccount.builder()
                .userId(user)
                .balance(new BigDecimal("100.00"))
                .currency(Currency.DOLLAR)
                .iban("vmalkaalaavvv")
                .build();
       this.bankAccount=bankAccountRepository.save(bankAccount);

        var account = BankAccount.builder()
                .userId(user)
                .balance(new BigDecimal("100.00"))
                .currency(Currency.EURO)
                .iban("sasadcv")
                .build();
     this.account=   bankAccountRepository.save(account);

        var request = Request.builder()
                .bankAccount(account)
                .status(Status.PENDING)
                .salary(new BigDecimal("12999"))
                .feedback("ok")
                .build();
        this.request=requestRepository.save(request);

        var transaction = Transaction.builder()
                .transactionType(TransactionType.DEBIT)
                .amount(new BigDecimal("111"))
                .currency(Currency.DOLLAR)
                .bankAccount(account)
                .build();
      this.transaction = transactionRepository.save(transaction);
    }

    @AfterEach
    public void cleanUp(){
        requestRepository.deleteAll();
        cardRepository.deleteAll();
        transactionRepository.deleteAll();
        bankAccountRepository.deleteAll();
        userRepository.deleteAll();
    }



    @Test
    void create_Client() throws Exception {
        var client = UserRequest.builder()
                .username("JOHN")
                .password("{noop}12")
                .build();


        var clientView = UserView.builder()
                .id(null)
                .username("JOHn")
                .roles(Roles.CLIENT)
                .build();

       MvcResult result=  mockMvc.perform(MockMvcRequestBuilders.post("/api/banker/client/create")
                        .header(HttpHeaders.AUTHORIZATION, "Basic R0xFRDoxMjM0NTY=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientView)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

       String JsonResponseClientView = result.getResponse().getContentAsString();
       UserView userView= objectMapper.readValue(JsonResponseClientView, UserView.class);
        assertNotNull(userView.getId());
    }

    @Test
    void delete_Client() throws Exception {
        var client = User.builder()
                .username("ujku")
                .password("{noop}123456")
                .roles(Roles.CLIENT)
                .build();
        userRepository.save(client);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/banker/client/delete/"+client.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Basic R0xFRDoxMjM0NTY=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void update_client() throws Exception {
        var userRequest = UserRequest.builder()
                .id(user.getId())
                .username("gled")
                .password("{noop}123456}")
                .roles(Roles.CLIENT)
                .build();
        var userView = UserView.builder()
                .id(user.getId())
                .username("gled")
                .roles(Roles.CLIENT)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/banker/client/update")
                        .header(HttpHeaders.AUTHORIZATION, "Basic R0xFRDoxMjM0NTY=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(userView)));
    }

    @Test
    public void update_request() throws Exception {
        var account = BankAccount.builder()
                .balance(new BigDecimal("100"))
                .iban("122")
                .currency(Currency.EURO)
                .userId(user)
                .build();
        bankAccountRepository.save(account);
        var request = Request.builder()
                .bankAccount(account)
                .status(Status.PENDING)
                .salary(new BigDecimal("11111"))
                .build();
        requestRepository.save(request);
        var newRequest = RequestRequest.builder()
                .id(request.getId())
                .bankAccountId(account.getBankAccId())
                .accepted(true)
                .feedback("NICE")
                .salary(new BigDecimal("11111.0"))
                .build();
        var requestView = RequestView.builder()
                .id(request.getId())
                .status(Status.ACCEPTED)
                .feedback("NICE")
                .bankAccount(account)
                .salary(new BigDecimal("11111.0"))
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/banker/requests/update")
                        .header(HttpHeaders.AUTHORIZATION, "Basic R0xFRDoxMjM0NTY=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(requestView)));

    }

    @Test
    void bankaccount_create() throws Exception {

        var bankAccountRequest = BankAccountRequest.builder()
                .userId(user.getId())
                .balance(new BigDecimal("12345"))
                .currency(Currency.DOLLAR)
                .bban("1")
                .countryCode("tp")
                .build();

        var bankAccView = BankAccountView.builder()
                .id(null)
                .currency(Currency.DOLLAR)
                .balance(new BigDecimal("12345.0"))
                .iban("tp261")
                .build();

      MvcResult result=  mockMvc.perform(MockMvcRequestBuilders.post("/api/banker/bankaccount/create")
                        .header(HttpHeaders.AUTHORIZATION, "Basic R0xFRDoxMjM0NTY=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bankAccountRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
              .andReturn();

        String JsonResponse = result.getResponse().getContentAsString();
        BankAccountView responseBankAccountView = objectMapper.readValue(JsonResponse, BankAccountView.class);
        assertNotNull(responseBankAccountView.getId());
    }

    @Test
    void get_bankAccounts() throws Exception {
        List<BankAccountView> bankAccountViews = Arrays.asList(
               new BankAccountView(account.getBankAccId(), "sasadcv",Currency.EURO,new BigDecimal("100.00")),
                new BankAccountView(bankAccount.getBankAccId(), "vmalkaalaavvv",Currency.DOLLAR,new BigDecimal("100.00")));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/banker/bankaccount/get")
                        .header(HttpHeaders.AUTHORIZATION, "Basic R0xFRDoxMjM0NTY=")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(bankAccountViews)));

    }

    @Test
    void get_requests() throws Exception {
        List<RequestView> requestViews = Arrays.asList(
                new RequestView(request.getId(), Status.PENDING,new BigDecimal("12999.0"),"ok",account));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/banker/requests/get")
                        .header(HttpHeaders.AUTHORIZATION, "Basic R0xFRDoxMjM0NTY=")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(requestViews)));

    }

    @Test
    void get_transactions() throws Exception {
        List<TransactionView> transactionViews = Arrays.asList(
                new TransactionView(transaction.getId(),new BigDecimal("111.0"),Currency.DOLLAR,TransactionType.DEBIT,account));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/banker/transactions/get")
                        .header(HttpHeaders.AUTHORIZATION, "Basic R0xFRDoxMjM0NTY=")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(transactionViews)));

    }
}
