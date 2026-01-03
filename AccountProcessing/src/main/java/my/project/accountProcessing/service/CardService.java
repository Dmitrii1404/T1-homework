package my.project.accountProcessing.service;

import my.project.accountProcessing.dto.CardCreateDto;
import my.project.accountProcessing.entity.card.Card;

public interface CardService {

    void createCard(CardCreateDto cardCreateDto);

    Card getCardById(Long id);

}
