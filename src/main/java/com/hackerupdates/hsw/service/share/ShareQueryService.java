package com.hackerupdates.hsw.service.share;

import com.hackerupdates.hsw.dto.ShareDTO;
import com.hackerupdates.hsw.persistence.entity.Share;
import java.util.List;

public interface ShareQueryService {

    List<ShareDTO> list(List<Long> personIds, int pageNumber, int pageSize);

    List<ShareDTO> listFrom(List<Long> personIds, Long offset);

    Share findBy(Long id);
}
