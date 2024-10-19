package ProjectSpringBoot.Project.repo;

import ProjectSpringBoot.Project.entity.BankAccount;
import ProjectSpringBoot.Project.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {

    boolean existsByBankAccount( BankAccount bankAccountId );
    List<Card> findByBankAccount(BankAccount bankAccountId );

}
