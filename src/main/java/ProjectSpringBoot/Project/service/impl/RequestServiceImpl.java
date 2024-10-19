package ProjectSpringBoot.Project.service.impl;

import ProjectSpringBoot.Project.common.Status;
import ProjectSpringBoot.Project.controller.requests.BankAccountRequest;
import ProjectSpringBoot.Project.controller.requests.RequestRequest;
import ProjectSpringBoot.Project.entity.Request;
import ProjectSpringBoot.Project.exception.NoBankAccountFoundException;
import ProjectSpringBoot.Project.exception.NoRequestFoundException;
import ProjectSpringBoot.Project.mapper.BankAccountMapper;
import ProjectSpringBoot.Project.mapper.RequestMapper;
import ProjectSpringBoot.Project.repo.BankAccountRepository;
import ProjectSpringBoot.Project.repo.RequestRepository;
import ProjectSpringBoot.Project.repo.UserRepository;
import ProjectSpringBoot.Project.service.CardService;
import ProjectSpringBoot.Project.service.RequestService;
import ProjectSpringBoot.Project.view.RequestView;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RequestServiceImpl implements RequestService {

    public final RequestRepository requestRepository;
    public final RequestMapper requestMapper;
    public final CardService cardService;
    public final UserRepository userRepository;
    public final BankAccountRepository bankAccountRepository;
    public final BankAccountMapper bankAccountMapper;

    @Override
    public List<RequestView> findAllRequests() {
        return requestRepository.findAll()
                .stream()
                .map(requestMapper::mapEntityToView)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public RequestView update(RequestRequest request) {
        var requestDBOptional = requestRepository.findById(request.getId());
        if (requestDBOptional.isEmpty()) {
            throw new NoRequestFoundException("No request found with the id" + request.getId());
        }
        var requestDb = requestDBOptional.get();
        if (Status.PENDING.equals(requestDb.getStatus()) && request.isAccepted()) {
            requestDb.setStatus(Status.ACCEPTED);
            cardService.createDebitCard(requestDb.getBankAccount().getBankAccId());
        } else {
            requestDb.setStatus(Status.REJECTED);
        }
        requestDb.setFeedback(request.getFeedback());
        var updatedRequest = requestRepository.save(requestDb);
        return requestMapper.mapEntityToView(updatedRequest);
    }

    @Transactional
    @Override
    public RequestView createRequest(BankAccountRequest request) {
        var account = bankAccountRepository.findById(request.getBankAccId());
        if (account.isEmpty())
            throw new NoBankAccountFoundException("No Bank Account found with id: " + request.getBankAccId());
        var newCardRequest = Request.builder()
                .bankAccount(account.get())
                .salary(request.getSalary())
                .build();
        if (request.getSalary().doubleValue() < 500) {
            newCardRequest.setStatus(Status.REJECTED);
        } else {
            newCardRequest.setStatus(Status.PENDING);
        }
        newCardRequest = requestRepository.save(newCardRequest);
        return requestMapper.mapEntityToView(newCardRequest);
    }

    @Override
    public List<RequestView> getRequestByUserId(Integer userID) {
        var user = userRepository.findById(userID).get();
        var bankAccounts = bankAccountRepository.findAllByUserId(user);
        if (bankAccounts.isEmpty()) {
            throw new NoBankAccountFoundException("No bank accounts for user with id: " + userID);
        }
        List<Request> requestList = new ArrayList<>();
        bankAccounts.forEach(
                bankAccount -> {
                    requestList.addAll(requestRepository.findByBankAccount(bankAccount));
                }
        );
        return requestList.stream()
                .map(requestMapper::mapEntityToView)
                .collect(Collectors.toList());
    }
}

