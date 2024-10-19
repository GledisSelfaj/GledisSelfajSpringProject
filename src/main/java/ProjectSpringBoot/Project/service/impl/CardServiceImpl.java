package ProjectSpringBoot.Project.service.impl;

import ProjectSpringBoot.Project.common.CardType;
import ProjectSpringBoot.Project.entity.Card;
import ProjectSpringBoot.Project.exception.CardHasBankAccountException;
import ProjectSpringBoot.Project.exception.NoBankAccountFoundException;
import ProjectSpringBoot.Project.exception.NoUserFoundException;
import ProjectSpringBoot.Project.mapper.CardMapper;
import ProjectSpringBoot.Project.repo.BankAccountRepository;
import ProjectSpringBoot.Project.repo.CardRepository;
import ProjectSpringBoot.Project.repo.UserRepository;
import ProjectSpringBoot.Project.service.CardNumberService;
import ProjectSpringBoot.Project.service.CardService;
import ProjectSpringBoot.Project.view.CardView;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CardServiceImpl implements CardService {

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final CardNumberService cardNumberService;
    private final CardMapper cardMapper;

    @Transactional
    @Override
    public CardView createDebitCard(Integer bankAccountId) {
        if (bankAccountId == null) {
            throw new NoBankAccountFoundException("Bank Account Id is null");
        }
        var bankAccountDb = bankAccountRepository.findById(bankAccountId);
        if (bankAccountDb.isEmpty()) {
            throw new NoBankAccountFoundException("Bank Account is not found ");
        }
        if (cardRepository.existsByBankAccount(bankAccountDb.get())) {
            throw new CardHasBankAccountException("Bank Account is connected to debit card already");
        }
        var bankAccount = bankAccountDb.get();
        var card = Card.builder()
                .bankAccount(bankAccount)
                .cardNumber(cardNumberService.getCardNumber())
                .cardHolder(bankAccount.getUserId().getUsername())
                .creditCardType(CardType.DEBIT)
                .build();
        var cardDb = cardRepository.save(card);
        return cardMapper.mapEntityToView(cardDb);
    }

    @Override
    public List<CardView> getCardByUserId(Integer userID) {
        var userDb = userRepository.findById(userID);
        if (userDb.isEmpty()) {
            throw new NoUserFoundException("User is not found ");
        }
        var bankAccounts = bankAccountRepository.findAllByUserId(userDb.get());
        List<Card> cardsList = new ArrayList<>();
        bankAccounts.forEach(
           card -> {
               cardsList.addAll(cardRepository.findByBankAccount(card));
           });
        return cardsList.stream()
              .map(cardMapper::mapEntityToView)
               .collect(Collectors.toList());
    }
}
