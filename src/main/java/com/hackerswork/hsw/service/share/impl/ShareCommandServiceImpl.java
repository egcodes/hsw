package com.hackerswork.hsw.service.share.impl;

import com.hackerswork.hsw.enums.ValidationRule;
import com.hackerswork.hsw.exception.HswException;
import com.hackerswork.hsw.persistence.entity.Share;
import com.hackerswork.hsw.persistence.repository.ShareRepository;
import com.hackerswork.hsw.service.share.ShareCommandService;
import java.time.Instant;
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
