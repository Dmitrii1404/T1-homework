package my.project.clientProcessing.service;

import my.project.clientProcessing.dto.CardCreateDto;

public interface CardService {

    void createCard(Long clientProductId, CardCreateDto cardCreateDto);

}