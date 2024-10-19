package ProjectSpringBoot.Project.mapper;

import ProjectSpringBoot.Project.controller.requests.BankAccountRequest;
import ProjectSpringBoot.Project.entity.BankAccount;
import ProjectSpringBoot.Project.view.BankAccountView;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapper {

    public BankAccountView mapEntityToView(BankAccount bankAccount) {
        return BankAccountView.builder()
                .balance(bankAccount.getBalance())
                .iban(bankAccount.getIban())
                .currency(bankAccount.getCurrency())
                .id(bankAccount.getBankAccId())
                .build();
    }
    public BankAccount mapRequestToEntity(BankAccountRequest bankAccountRequest) {
        return BankAccount.builder()
                .balance(bankAccountRequest.getBalance())
                .iban(bankAccountRequest.getIban())
                .currency(bankAccountRequest.getCurrency())
                .BankAccId(bankAccountRequest.getBankAccId())
                .build();
    }
}
