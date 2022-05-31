package com.hackerswork.hsw.service.share.impl;

import com.hackerswork.hsw.persistence.entity.Share;
import com.hackerswork.hsw.persistence.repository.ShareRepository;
import com.hackerswork.hsw.service.share.ShareCommandService;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareCommandServiceImpl implements ShareCommandService {

    private final ShareRepository shareRepository;

    @Override
    public Share add(Long personId, Share share) {
        share.setCreatedTime(OffsetDateTime.now());
        share.setPersonId(personId);
        return shareRepository.save(share);
    }
}
