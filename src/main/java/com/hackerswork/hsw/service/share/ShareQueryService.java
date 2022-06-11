package com.hackerswork.hsw.service.share;

import com.hackerswork.hsw.dto.ShareDTO;
import com.hackerswork.hsw.persistence.entity.Share;
import java.util.List;
import java.util.Optional;

public interface ShareQueryService {

    List<ShareDTO> list(List<Long> personIds, int pageNumber, int pageSize);

    List<ShareDTO> listFrom(List<Long> personIds, Long offset);

    Optional<Share> findBy(Long id);
}
