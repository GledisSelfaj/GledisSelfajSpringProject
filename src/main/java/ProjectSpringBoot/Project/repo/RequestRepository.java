package ProjectSpringBoot.Project.repo;

import ProjectSpringBoot.Project.entity.BankAccount;
import ProjectSpringBoot.Project.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request,Integer> {
    List<Request> findByBankAccount(BankAccount bankAccount);
}
