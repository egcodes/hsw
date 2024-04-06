package com.hackerupdates.hsw.service.share;

import com.hackerupdates.hsw.persistence.entity.Share;

public interface ShareCommandService {

    Share add(Long personId, Share share);

    void delete(Long personId, Long id);

}
