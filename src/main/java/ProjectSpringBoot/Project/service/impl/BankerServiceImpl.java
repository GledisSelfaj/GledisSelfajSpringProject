package ProjectSpringBoot.Project.service.impl;

import ProjectSpringBoot.Project.common.Roles;
import ProjectSpringBoot.Project.controller.requests.BankAccountRequest;
import ProjectSpringBoot.Project.controller.requests.RequestRequest;
import ProjectSpringBoot.Project.controller.requests.UserRequest;
import ProjectSpringBoot.Project.exception.NoClientFoundException;
import ProjectSpringBoot.Project.exception.NoUserFoundException;
import ProjectSpringBoot.Project.service.BankAccountService;
import ProjectSpringBoot.Project.service.BankerService;
import ProjectSpringBoot.Project.service.RequestService;
import ProjectSpringBoot.Project.service.TransactionService;
import ProjectSpringBoot.Project.service.UserService;
import ProjectSpringBoot.Project.view.BankAccountView;
import ProjectSpringBoot.Project.view.RequestView;
import ProjectSpringBoot.Project.view.TransactionView;
import ProjectSpringBoot.Project.view.UserView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BankerServiceImpl implements BankerService {

    private final UserService userService;
    private final BankAccountService bankAccountService;
    private final RequestService requestService;
    private final TransactionService transactionService;

    @Override
    public RequestView update(RequestRequest request) {
        return requestService.update(request);
    }

    @Override
    public List<RequestView> findAllRequests() {
        return requestService.findAllRequests();
    }

    @Override
    public List<BankAccountView> findAllBankAccount() {
        return bankAccountService.findAllBankAccount();
    }

    @Override
    public List<TransactionView> findAllTransactions() {
        return transactionService.findAllTransactions();
    }

    @Override
    public UserView createClient(UserRequest clientRequest) {
        clientRequest.setRoles(Roles.CLIENT);
        return userService.save(clientRequest);
    }

    @Override
    public void deleteById(@PathVariable Integer Id) {
        var client = userService.findById(Id);
        if (client == null) {
            throw new NoUserFoundException("No User found with the id" + Id);
        }
        if (client.getRoles().equals(Roles.CLIENT)) {
            userService.deleteById(client.getId());
        } else {
            throw new NoClientFoundException("No Client found with the id" + Id);
        }
    }

    @Override
    public BankAccountView createBankAccount(BankAccountRequest bankAccountRequest) {
        return bankAccountService.createBankAccount(bankAccountRequest);
    }

    @Override
    public UserView updateClient(UserRequest userRequest) {
        if (userRequest.getRoles().equals(Roles.CLIENT)) {
            return userService.save(userRequest);
        }
        throw new NoClientFoundException("No Client found with the id" + userRequest.getId());
    }
}

