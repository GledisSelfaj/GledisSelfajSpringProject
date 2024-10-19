package ProjectSpringBoot.Project.rest;

import ProjectSpringBoot.Project.common.CardType;
import ProjectSpringBoot.Project.common.Currency;
import ProjectSpringBoot.Project.common.Roles;
import ProjectSpringBoot.Project.common.Status;
import ProjectSpringBoot.Project.common.TransactionType;
import ProjectSpringBoot.Project.controller.requests.BankAccountRequest;
import ProjectSpringBoot.Project.controller.requests.TransactionRequest;
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
import ProjectSpringBoot.Project.service.CardNumberService;
import ProjectSpringBoot.Project.service.IbanService;
import ProjectSpringBoot.Project.view.BankAccountView;
import ProjectSpringBoot.Project.view.CardView;
import ProjectSpringBoot.Project.view.RequestView;
import ProjectSpringBoot.Project.view.TransactionView;
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
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CardNumberService cardNumberService;
    private User client;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private UserRepository userRepository;
    private Integer bankAccountId;
    private BankAccount bankAccount;
    private BankAccount bankAccount2;
    private Transaction transaction;
    @Autowired
    private IbanService ibanService;

    @BeforeEach
    public void setUp() {
        var user = User.builder()
                .username("Gled")
                .password("{noop}12tp")
                .roles(Roles.CLIENT)
                .build();
        client = userRepository.save(user);

        var bankaccount = BankAccount.builder()
                .userId(user)
                .balance(new BigDecimal("1000"))
                .currency(Currency.EURO)
                .iban("11md44")
                .build();
        this.bankAccount = bankAccountRepository.save(bankaccount);
        this.bankAccountId = bankaccount.getBankAccId();

        var transaction = Transaction.builder()
                .bankAccount(this.bankAccount)
                .transactionType(TransactionType.DEBIT)
                .amount(new BigDecimal("400"))
                .currency(Currency.EURO)
                .build();
        this.transaction = transactionRepository.save(transaction);

        var bankAcc = BankAccount.builder()
                .userId(user)
                .iban("gls98")
                .currency(Currency.EURO)
                .balance(new BigDecimal("800.0"))
                .build();
        bankAccount2 = bankAccountRepository.save(bankAcc);
    }

    @AfterEach
    public void cleanUp() {
        requestRepository.deleteAll();
        transactionRepository.deleteAll();
        cardRepository.deleteAll();
        bankAccountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void create_card() throws Exception {
        var cardView = CardView.builder()
                .id(null)
                .cardNumber(cardNumberService.getCardNumber())
                .cardHolder("Gled")
                .creditCardType(CardType.DEBIT)
                .bankAccount(bankAccount)
                .build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/client/cards/create/" + bankAccountId)
                        .header(HttpHeaders.AUTHORIZATION, "Basic R2xlZDoxMnRw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardView)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        CardView responseCardView = objectMapper.readValue(jsonResponse, CardView.class);
        assertNotNull(responseCardView.getId());
    }

    @Test
    void create_transaction() throws Exception {
        var card = Card.builder()
                .bankAccount(bankAccount)
                .cardNumber(cardNumberService.getCardNumber())
                .creditCardType(CardType.DEBIT)
                .cardHolder("gled")
                .build();
         cardRepository.save(card);
        var receiverAccount = BankAccount.builder()
                .userId(client)
                .balance(new BigDecimal("1000"))
                .currency(Currency.EURO)
                .iban("1mm")
                .build();
        bankAccountRepository.save(receiverAccount);

        var transactionRequest = TransactionRequest.builder()
                .amount(new BigDecimal("400"))
                .senderBankAccountId(bankAccountId)
                .receiverIban("1mm")
                .build();

        var bankAccView = BankAccountView.builder()
                .balance(new BigDecimal(600))
                .currency(Currency.EURO)
                .iban(bankAccount.getIban())
                .id(bankAccountId)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/client/transaction/create")
                        .content(objectMapper.writeValueAsString(transactionRequest))
                        .header(HttpHeaders.AUTHORIZATION, "Basic R2xlZDoxMnRw")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(bankAccView)));
    }

    @Test
    void create_request() throws Exception {
        var bankAccount = BankAccount.builder()
                .currency(Currency.EURO)
                .balance(new BigDecimal("1001"))
                .userId(client)
                .iban(ibanService.generateIban("tp","alb1"))
                .build();
        bankAccount = bankAccountRepository.save(bankAccount);
        var bankRequest = BankAccountRequest.builder()
                .BankAccId(bankAccount.getBankAccId())
                .currency(bankAccount.getCurrency())
                .bban("alb1")
                .salary(new BigDecimal("1000"))
                .countryCode("tp")
                .userId(bankAccount.getUserId().getId())
                .build();
        var requestView = RequestView.builder()
                .id(null)
                .bankAccount(bankAccount)
                .salary(new BigDecimal("1000"))
                .status(Status.PENDING)
                .build();

       MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.post("/api/client/request/create")
                        .header(HttpHeaders.AUTHORIZATION, "Basic R2xlZDoxMnRw")
                        .content(objectMapper.writeValueAsString(bankRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
       String jsonResponse = result.getResponse().getContentAsString();
       RequestView responseRequestView = objectMapper.readValue(jsonResponse,RequestView.class);
       assertNotNull(responseRequestView.getId());

    }


    @Test
    void get_bank_account() throws Exception {
        List<BankAccountView> bankAccounts = Arrays.asList(
                new BankAccountView(bankAccount.getBankAccId(), "11md44", Currency.EURO, new BigDecimal("1000.0")),
                new BankAccountView(bankAccount2.getBankAccId(), "gls98", Currency.EURO, new BigDecimal("800.0"))
        );

        mockMvc.perform(MockMvcRequestBuilders.get("/api/client/bankaccount/get/" + client.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Basic R2xlZDoxMnRw")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(bankAccounts)));
    }

    @Test
    void get_card() throws Exception {

        var card = Card.builder()
                .cardHolder("JOHN")
                .creditCardType(CardType.DEBIT)
                .cardNumber(cardNumberService.getCardNumber())
                .bankAccount(bankAccount)
                .build();
         cardRepository.save(card).getId();
        List<CardView> cards = Arrays.asList(
                new CardView(card.getId(), "JOHN", CardType.DEBIT, cardNumberService.getCardNumber(), bankAccount));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/client/cards/get/" + client.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Basic R2xlZDoxMnRw")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(cards)));
    }

    @Test
    void get_transaction() throws Exception {
        List<TransactionView> transactions = Arrays.asList(
                new TransactionView(transaction.getId(), new BigDecimal("400.0"), Currency.EURO, TransactionType.DEBIT, bankAccount)
        );

        mockMvc.perform(MockMvcRequestBuilders.get("/api/client/transaction/get/" + bankAccount.getBankAccId())
                        .header(HttpHeaders.AUTHORIZATION, "Basic R2xlZDoxMnRw")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(transactions)));
    }

    @Test
    void get_requests() throws Exception {
        var newBankAccount = BankAccount.builder()
                .currency(Currency.DOLLAR)
                .balance(new BigDecimal("1000.0"))
                .userId(client)
                .iban("tp1")
                .build();
        newBankAccount = bankAccountRepository.save(newBankAccount);
        var requests = Request.builder()
                .bankAccount(newBankAccount)
                .salary(new BigDecimal("1000"))
                .status(Status.ACCEPTED)
                .feedback("OK")
                .build();
        requests = requestRepository.save(requests);
        List<RequestView> request = Arrays.asList(
                new RequestView(requests.getId(), Status.ACCEPTED, new BigDecimal("1000.0"), "OK", newBankAccount));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/client/request/get/" + client.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Basic R2xlZDoxMnRw")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(request)));
    }
}
