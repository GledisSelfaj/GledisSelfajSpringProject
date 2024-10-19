package ProjectSpringBoot.Project.service;


import ProjectSpringBoot.Project.controller.requests.BankAccountRequest;
import ProjectSpringBoot.Project.view.BankAccountView;

import java.util.List;

public interface BankAccountService {

     BankAccountView createBankAccount(BankAccountRequest bankAccountRequest );
     List<BankAccountView> findAllBankAccount();
     List<BankAccountView> getBankAccountByUserId(Integer userID);
}
