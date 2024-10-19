package ProjectSpringBoot.Project.service.impl;

import ProjectSpringBoot.Project.controller.requests.BankAccountRequest;
import ProjectSpringBoot.Project.controller.requests.TransactionRequest;
import ProjectSpringBoot.Project.service.BankAccountService;
import ProjectSpringBoot.Project.service.CardService;
import ProjectSpringBoot.Project.service.ClientService;
import ProjectSpringBoot.Project.service.RequestService;
import ProjectSpringBoot.Project.service.TransactionService;
import ProjectSpringBoot.Project.view.BankAccountView;
import ProjectSpringBoot.Project.view.CardView;
import ProjectSpringBoot.Project.view.RequestView;
import ProjectSpringBoot.Project.view.TransactionView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    private final TransactionService transactionService;
    private final BankAccountService bankAccountService;
    private final CardService cardService;
    private final RequestService requestService;

    @Override
    public CardView createDebitCard(Integer bankAccountId) {
        return cardService.createDebitCard(bankAccountId);
    }

    @Override
    public RequestView createRequest(BankAccountRequest request) {
        return requestService.createRequest(request);
    }

    @Override
    public List<RequestView> getRequestByUserId(Integer userID) {
        return requestService.getRequestByUserId(userID);
    }

    @Override
    public List<BankAccountView> getBankAccountByUserId(Integer userID) {
        return bankAccountService.getBankAccountByUserId(userID);
    }

    @Override
    public List<CardView> getCardByUserId(Integer userID) {
        return cardService.getCardByUserId(userID);
    }

    @Override
    public List<TransactionView> getTransactionsByBankAccountId(Integer bankAccountId) {
        return transactionService.getTransactionsByBankAccountId(bankAccountId);
    }

    @Override
    public BankAccountView createTransaction(TransactionRequest transactionRequest) {
        return transactionService.transaction(transactionRequest);
    }
}
