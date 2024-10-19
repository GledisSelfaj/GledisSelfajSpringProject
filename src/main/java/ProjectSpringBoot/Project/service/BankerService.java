package ProjectSpringBoot.Project.service;

import ProjectSpringBoot.Project.controller.requests.BankAccountRequest;
import ProjectSpringBoot.Project.controller.requests.RequestRequest;
import ProjectSpringBoot.Project.controller.requests.UserRequest;
import ProjectSpringBoot.Project.view.BankAccountView;
import ProjectSpringBoot.Project.view.RequestView;
import ProjectSpringBoot.Project.view.TransactionView;
import ProjectSpringBoot.Project.view.UserView;

import java.util.List;

public interface  BankerService {

    UserView createClient(UserRequest userRequest);
    RequestView update(RequestRequest request);
    List<RequestView> findAllRequests();
    List<BankAccountView> findAllBankAccount();
    List<TransactionView> findAllTransactions();
    void deleteById(Integer Id);
    BankAccountView createBankAccount(BankAccountRequest bankAccountRequest );
    UserView updateClient(UserRequest userRequest);
}
