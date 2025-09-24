package my.project.clientProcessing.service;

import my.project.clientProcessing.dto.ClientCreateDto;
import my.project.clientProcessing.dto.ClientResponseDto;
import my.project.clientProcessing.dto.UserResponseDto;
import my.project.clientProcessing.entity.client.Client;

public interface ClientService {

    UserResponseDto clientRegistry(ClientCreateDto clientCreateDto);
    Client getClientByDocumentId(String documentId);
    ClientResponseDto getClientById(Long id);

}
