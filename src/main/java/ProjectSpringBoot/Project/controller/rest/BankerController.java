package ProjectSpringBoot.Project.controller.rest;

import ProjectSpringBoot.Project.controller.requests.BankAccountRequest;
import ProjectSpringBoot.Project.controller.requests.RequestRequest;
import ProjectSpringBoot.Project.controller.requests.UserRequest;
import ProjectSpringBoot.Project.service.BankerService;
import ProjectSpringBoot.Project.utils.AppConstants;
import ProjectSpringBoot.Project.view.BankAccountView;
import ProjectSpringBoot.Project.view.RequestView;
import ProjectSpringBoot.Project.view.TransactionView;
import ProjectSpringBoot.Project.view.UserView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@org.springframework.web.bind.annotation.RestController
@RequestMapping(AppConstants.API_BANKER)
@RequiredArgsConstructor
public class BankerController {

    private final BankerService bankerService;

    @PostMapping(AppConstants.CLIENT_CREATE)
    public ResponseEntity<UserView> createUser(@RequestBody UserRequest clientRequest) {
        return ResponseEntity.ok(bankerService.createClient(clientRequest));
    }

    @DeleteMapping(AppConstants.DELETE_CLIENT)
    public ResponseEntity<UserView> deleteClient(@PathVariable Integer id) {
        bankerService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping(AppConstants.UPDATE_CLIENT)
    public ResponseEntity<UserView> updateBanker(@RequestBody UserRequest client) {
        return ResponseEntity.ok(bankerService.updateClient(client));
    }

    @PutMapping(AppConstants.UPDATE_REQUESTS)
    public ResponseEntity<RequestView> updateRequests(@RequestBody RequestRequest request) {
        return ResponseEntity.ok(bankerService.update(request));
    }

    @GetMapping(AppConstants.BANK_ACCOUNTS_GET)
    public ResponseEntity<List<BankAccountView>> getAllBankAccounts() {
        return ResponseEntity.ok(bankerService.findAllBankAccount());
    }

    @PostMapping(AppConstants.BANK_ACCOUNT_CREATE)
    public BankAccountView createBankAccount(@RequestBody BankAccountRequest bankAccountRequest) {
        return bankerService.createBankAccount(bankAccountRequest);
    }

    @GetMapping(AppConstants.GET_REQUESTS)
    public ResponseEntity<List<RequestView>> getAllRequests() {
        return ResponseEntity.ok(bankerService.findAllRequests());
    }

    @GetMapping(AppConstants.GET_TRANSACTIONS)
    public ResponseEntity<List<TransactionView>> getAllTransactions() {
        return ResponseEntity.ok(bankerService.findAllTransactions());
    }
}
