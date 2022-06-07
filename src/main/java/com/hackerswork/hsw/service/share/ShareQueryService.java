package com.hackerswork.hsw.service.share;

import com.hackerswork.hsw.dto.ShareDTO;
import java.util.List;

public interface ShareQueryService {

    List<ShareDTO> list(List<Long> personIds, int pageNumber, int pageSize);
}
