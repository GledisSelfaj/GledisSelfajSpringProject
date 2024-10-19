package ProjectSpringBoot.Project.mapper;

import ProjectSpringBoot.Project.entity.Transaction;
import ProjectSpringBoot.Project.view.TransactionView;
import org.springframework.stereotype.Service;

@Service
public class TransactionMapper {

    public TransactionView mapEntityToView(Transaction transaction) {
        return TransactionView.builder()
                .id(transaction.getId())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .bankAccount(transaction.getBankAccount())
                .currency(transaction.getCurrency())
                .build();
    }
}
