package ProjectSpringBoot.Project.repo;


import ProjectSpringBoot.Project.entity.BankAccount;
import ProjectSpringBoot.Project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

    List<BankAccount> findAllByUserId(User user);
    BankAccount findBankAccountByIban(String Iban);
}
