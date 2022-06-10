package com.hackerswork.hsw.service.share.impl;

import com.hackerswork.hsw.dto.ShareDTO;
import com.hackerswork.hsw.persistence.repository.ShareRepository;
import com.hackerswork.hsw.service.share.ShareQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareQueryServiceImpl implements ShareQueryService {

    private final ShareRepository shareRepository;

    @Override
    public List<ShareDTO> list(List<Long> personIds, int pageNumber, int pageSize) {
        return shareRepository.findAllByPersonIds(personIds, PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public List<ShareDTO> list(List<Long> personIds, Long offset) {
        return shareRepository.findByOffsetAndPersonIds(personIds, offset);
    }
}
