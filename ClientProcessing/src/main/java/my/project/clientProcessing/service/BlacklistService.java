package my.project.clientProcessing.service;

import my.project.clientProcessing.dto.DocumentCheckBlacklistDto;

public interface BlacklistService {

    Boolean documentIsBanned(DocumentCheckBlacklistDto checkBlacklistDto);

}
