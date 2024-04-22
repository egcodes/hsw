package com.hackerupdates.hsw.service.share;

import com.hackerupdates.hsw.constants.Constant;
import com.hackerupdates.hsw.domain.dto.ShareDTO;
import com.hackerupdates.hsw.enums.ValidationRule;
import com.hackerupdates.hsw.exception.HswException;
import com.hackerupdates.hsw.domain.entity.Share;
import com.hackerupdates.hsw.domain.repository.ShareRepository;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShareQueryService {

    private final ShareRepository shareRepository;

    public List<ShareDTO> listByPersonIds(List<Long> personIds, int pageNumber, int pageSize) {
        return shareRepository.findAllByPersonIds(personIds, PageRequest.of(pageNumber, pageSize));
    }

    public List<ShareDTO> listByPersonIdsFrom(List<Long> personIds, Long offset) {
        return shareRepository.findByOffsetAndPersonIds(personIds, offset, PageRequest.of(0, Constant.OFFSET_LIMIT));
    }

    public List<ShareDTO> list(int pageNumber, int pageSize) {
        return shareRepository.findAllShares(PageRequest.of(pageNumber, pageSize));
    }

    public List<ShareDTO> listFrom(Long offset) {
        return shareRepository.findAllByOffset(offset, PageRequest.of(0, Constant.OFFSET_LIMIT));
    }

    public Share findBy(Long id) {
        return shareRepository.findById(id)
            .orElseThrow(() -> new HswException(ValidationRule.SHARE_NOT_FOUND));
    }
}
