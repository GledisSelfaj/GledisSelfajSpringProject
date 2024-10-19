package ProjectSpringBoot.Project.mapper;

import ProjectSpringBoot.Project.controller.requests.CardRequest;
import ProjectSpringBoot.Project.entity.Card;
import ProjectSpringBoot.Project.view.CardView;
import org.springframework.stereotype.Service;

@Service
public class CardMapper {

    public CardView mapEntityToView(Card card) {
        return CardView.builder()
                .id(card.getId())
                .bankAccount(card.getBankAccount())
                .cardNumber(card.getCardNumber())
                .creditCardType(card.getCreditCardType())
                .cardHolder(card.getCardHolder())
                .build();
    }
    public Card mapRequestToEntity(CardRequest cardRequest) {
        return Card.builder()
                .id(cardRequest.getId())
                .bankAccount(cardRequest.getBankAccount())
                .cardNumber(cardRequest.getCardNumber())
                .creditCardType(cardRequest.getCreditCardType())
                .build();
    }
}
