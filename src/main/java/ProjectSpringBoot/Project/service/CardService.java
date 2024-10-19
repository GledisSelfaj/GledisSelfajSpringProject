package ProjectSpringBoot.Project.service;

import ProjectSpringBoot.Project.view.CardView;

import java.util.List;

public interface CardService {
    List<CardView> getCardByUserId(Integer userId);
    CardView createDebitCard(Integer bankAccountId);
}
