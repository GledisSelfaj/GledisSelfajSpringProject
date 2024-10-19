package ProjectSpringBoot.Project.service.impl;

import ProjectSpringBoot.Project.common.TransactionType;
import ProjectSpringBoot.Project.controller.requests.TransactionRequest;
import ProjectSpringBoot.Project.entity.BankAccount;
import ProjectSpringBoot.Project.entity.Transaction;
import ProjectSpringBoot.Project.exception.NoBankAccountFoundException;
import ProjectSpringBoot.Project.exception.NoCardFoundException;
import ProjectSpringBoot.Project.exception.SubtractedAmountIsGreaterThanBalanceException;
import ProjectSpringBoot.Project.mapper.BankAccountMapper;
import ProjectSpringBoot.Project.mapper.TransactionMapper;
import ProjectSpringBoot.Project.repo.BankAccountRepository;
import ProjectSpringBoot.Project.repo.CardRepository;
import ProjectSpringBoot.Project.repo.TransactionRepository;
import ProjectSpringBoot.Project.service.TransactionService;
import ProjectSpringBoot.Project.view.BankAccountView;
import ProjectSpringBoot.Project.view.TransactionView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final CardRepository cardRepository;
    private final BankAccountMapper bankAccountMapper;
    private final TransactionMapper transactionMapper;

    @Transactional
    @Override
    public BankAccountView transaction(TransactionRequest transactionRequest) {
        var senderBankAccountId = transactionRequest.getSenderBankAccountId();
        var iban = transactionRequest.getReceiverIban();
        var amount = transactionRequest.getAmount();
        if (senderBankAccountId == null) {
            throw new NoBankAccountFoundException("Bank Account Id is null");
        }
        var senderBankAccountDb = bankAccountRepository.findById(senderBankAccountId);
        if (senderBankAccountDb.isEmpty()) {
            throw new NoBankAccountFoundException("Bank Account is not found ");
        }
        var senderBankAccount = senderBankAccountDb.get();
        var bankAccountHasCard = cardRepository.existsByBankAccount(senderBankAccount);
        if (!bankAccountHasCard) {
            throw new NoCardFoundException("No card found for this Bank Account");
        }
        var recieverBankAccount = bankAccountRepository.findBankAccountByIban(iban);
        if (recieverBankAccount == null) {
            throw new NoBankAccountFoundException("Bank Account is not found ");
        }
        calculateBalance(senderBankAccount, amount, TransactionType.DEBIT);
        calculateBalance(recieverBankAccount, amount, TransactionType.CREDIT);
        return bankAccountMapper.mapEntityToView(senderBankAccount);
    }

    private void calculateBalance(BankAccount bankAccount, BigDecimal amount, TransactionType type) {
        if (type.equals(TransactionType.DEBIT)) {
            if (bankAccount.getBalance().subtract(amount).doubleValue() < 0) {
                throw new SubtractedAmountIsGreaterThanBalanceException("Amount is greater than balance");
            }
            bankAccount.setBalance(bankAccount.getBalance().subtract(amount));
        } else {
            bankAccount.setBalance(bankAccount.getBalance().add(amount));
        }
        var transaction = Transaction.builder()
                .currency(bankAccount.getCurrency())
                .transactionType(type)
                .amount(amount)
                .bankAccount(bankAccount)
                .build();
        transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionView> findAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(transactionMapper::mapEntityToView)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionView> getTransactionsByBankAccountId(Integer bankAccountId) {
        var bankAccount = bankAccountRepository.findById(bankAccountId).get();
        return transactionRepository.findByBankAccount(bankAccount).stream()
                .map(transactionMapper::mapEntityToView)
                .collect(Collectors.toList());
    }
}
