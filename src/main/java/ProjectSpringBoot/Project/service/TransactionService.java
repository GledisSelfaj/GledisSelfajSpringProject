package ProjectSpringBoot.Project.service;


import ProjectSpringBoot.Project.controller.requests.TransactionRequest;
import ProjectSpringBoot.Project.view.BankAccountView;
import ProjectSpringBoot.Project.view.TransactionView;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    BankAccountView transaction(TransactionRequest transactionRequest);
    List<TransactionView> findAllTransactions();
    List<TransactionView> getTransactionsByBankAccountId(Integer bankAccountId);
}
