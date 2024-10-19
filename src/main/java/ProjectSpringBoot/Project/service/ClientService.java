package ProjectSpringBoot.Project.service;

import ProjectSpringBoot.Project.controller.requests.BankAccountRequest;
import ProjectSpringBoot.Project.controller.requests.TransactionRequest;
import ProjectSpringBoot.Project.view.BankAccountView;
import ProjectSpringBoot.Project.view.CardView;
import ProjectSpringBoot.Project.view.RequestView;
import ProjectSpringBoot.Project.view.TransactionView;

import java.util.List;

public interface ClientService {
    CardView createDebitCard(Integer bankAccountId);

    List<RequestView> getRequestByUserId(Integer userID);

    RequestView createRequest(BankAccountRequest request);

    List<BankAccountView> getBankAccountByUserId(Integer userID);

    List<CardView> getCardByUserId(Integer userID);

    List<TransactionView> getTransactionsByBankAccountId(Integer bankAccountId);

    BankAccountView createTransaction(TransactionRequest transactionRequest);

}
