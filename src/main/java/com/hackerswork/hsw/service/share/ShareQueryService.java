package com.hackerswork.hsw.service.share;

import com.hackerswork.hsw.persistence.entity.Share;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface ShareQueryService {

    List<Share> findByPersonId(Long personId);
}
