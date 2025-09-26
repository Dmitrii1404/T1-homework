package my.project.clientProcessing.service.impl;

import lombok.RequiredArgsConstructor;
import my.project.clientProcessing.dto.DocumentCheckBlacklistDto;
import my.project.clientProcessing.repository.BlacklistRepository;
import my.project.clientProcessing.service.BlacklistService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlacklistServiceImpl implements BlacklistService {

    private final BlacklistRepository blacklistRepository;

    @Override
    @Transactional(readOnly = true)
    public Boolean documentIsBanned(DocumentCheckBlacklistDto checkBlacklistDto) {
        return blacklistRepository.existsByDocumentId(checkBlacklistDto.documentId());
    }
}
