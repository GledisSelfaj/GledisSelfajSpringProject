package ProjectSpringBoot.Project.service.impl;

import ProjectSpringBoot.Project.controller.requests.BankAccountRequest;
import ProjectSpringBoot.Project.entity.BankAccount;
import ProjectSpringBoot.Project.exception.NoUserFoundException;
import ProjectSpringBoot.Project.mapper.BankAccountMapper;
import ProjectSpringBoot.Project.repo.BankAccountRepository;
import ProjectSpringBoot.Project.repo.UserRepository;
import ProjectSpringBoot.Project.service.BankAccountService;
import ProjectSpringBoot.Project.service.IbanService;
import ProjectSpringBoot.Project.view.BankAccountView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;
    private final UserRepository userRepository;
    private final IbanService ibanService;

    @Transactional
    @Override
    public BankAccountView createBankAccount(BankAccountRequest bankAccountRequest) {
        if(bankAccountRequest.getUserId()==null){
            throw new NoUserFoundException("No user found");
        }
        var user = userRepository.findById(bankAccountRequest.getUserId());
        var bankAccount = BankAccount.builder()
                .userId(user.get())
                .iban(ibanService.generateIban(bankAccountRequest.getCountryCode() , bankAccountRequest.getBban()))
                .currency(bankAccountRequest.getCurrency())
                .balance(bankAccountRequest.getBalance())
                .build();
        bankAccountRepository.save(bankAccount);
        return bankAccountMapper.mapEntityToView(bankAccount);
    }

    @Override
    public List<BankAccountView> findAllBankAccount() {
        var bankAccountsDb = bankAccountRepository.findAll();
        return bankAccountsDb
                .stream()
                .map(bankAccountMapper::mapEntityToView)
                .collect(Collectors.toList());
    }

    @Override
    public List<BankAccountView> getBankAccountByUserId(Integer userID){
        var user = userRepository.findById(userID).get();
        return bankAccountRepository.findAllByUserId(user).stream()
                .map(bankAccountMapper::mapEntityToView)
                .collect(Collectors.toList());
    }
}
