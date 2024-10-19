package ProjectSpringBoot.Project.controller.rest;

import ProjectSpringBoot.Project.controller.requests.BankAccountRequest;
import ProjectSpringBoot.Project.controller.requests.TransactionRequest;
import ProjectSpringBoot.Project.service.ClientService;
import ProjectSpringBoot.Project.utils.AppConstants;
import ProjectSpringBoot.Project.view.BankAccountView;
import ProjectSpringBoot.Project.view.CardView;
import ProjectSpringBoot.Project.view.RequestView;
import ProjectSpringBoot.Project.view.TransactionView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(AppConstants.API_CLIENT)
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping(AppConstants.CARD_CREATE)
    public ResponseEntity<CardView> createCard(@PathVariable Integer bankAccountId) {
        return ResponseEntity.ok(clientService.createDebitCard(bankAccountId));
    }

    @PostMapping(AppConstants.CREATE_TRANSACTION)
    public ResponseEntity<BankAccountView> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        return ResponseEntity.ok(clientService.createTransaction(transactionRequest));
    }

    @PostMapping(AppConstants.CREATE_REQUEST)
    public ResponseEntity<RequestView> createRequest(@RequestBody BankAccountRequest request) {
        return ResponseEntity.ok(clientService.createRequest(request));
    }

    @GetMapping(AppConstants.BANK_ACCOUNT_GET)
    public ResponseEntity<List<BankAccountView>> getBankAccounts(@PathVariable Integer userId) {
        return ResponseEntity.ok(clientService.getBankAccountByUserId(userId));
    }

    @GetMapping(AppConstants.CARDS_GET)
    public ResponseEntity<List<CardView>> getCards(@PathVariable Integer userId) {
        return ResponseEntity.ok(clientService.getCardByUserId(userId));
    }

    @GetMapping(AppConstants.GET_TRANSACTION)
    public ResponseEntity<List<TransactionView>> getTransactions(@PathVariable Integer bankAccountId) {
        return ResponseEntity.ok(clientService.getTransactionsByBankAccountId(bankAccountId));
    }

    @GetMapping(AppConstants.GET_REQUEST)
    public ResponseEntity<List<RequestView>> getRequest(@PathVariable Integer userId) {
        return ResponseEntity.ok(clientService.getRequestByUserId(userId));
    }
}
