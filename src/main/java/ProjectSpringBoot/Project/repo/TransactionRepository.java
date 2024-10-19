package ProjectSpringBoot.Project.repo;

import ProjectSpringBoot.Project.entity.BankAccount;
import ProjectSpringBoot.Project.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByBankAccount(BankAccount bankAccountId);
}
