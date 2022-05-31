package com.hackerswork.hsw.service.share.impl;

import com.hackerswork.hsw.persistence.entity.Share;
import com.hackerswork.hsw.persistence.repository.ShareRepository;
import com.hackerswork.hsw.service.share.ShareQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareQueryServiceImpl implements ShareQueryService {

    private final ShareRepository shareRepository;

    @Override
    public List<Share> findByPersonId(Long personId) {
        return shareRepository.findByPersonId(personId);
    }
}
