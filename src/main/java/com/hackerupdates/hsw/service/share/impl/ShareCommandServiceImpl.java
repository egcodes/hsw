package com.hackerupdates.hsw.service.share.impl;

import com.hackerupdates.hsw.enums.ValidationRule;
import com.hackerupdates.hsw.exception.HswException;
import com.hackerupdates.hsw.persistence.entity.Share;
import com.hackerupdates.hsw.persistence.repository.ShareRepository;
import com.hackerupdates.hsw.service.share.ShareCommandService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShareCommandServiceImpl implements ShareCommandService {

    private final ShareRepository shareRepository;

    @Override
    public Share add(Long personId, Share share) {
        share.setCreatedTime(Instant.now());
        share.setPersonId(personId);
        return shareRepository.save(share);
    }

    @Override
    public void delete(Long personId, Long id) {
        var sharePossible = shareRepository.findById(id);
        if (sharePossible.isPresent()) {
            var share = sharePossible.get();
            if (share.getPersonId().equals(personId)) {
                shareRepository.deleteById(id);
            } else {
                throw new HswException(ValidationRule.UNAUTHORIZED_ACCESS);
            }
        } else {
            throw new HswException(ValidationRule.SHARE_NOT_FOUND);
        }
    }
}
