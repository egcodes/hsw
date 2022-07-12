package com.hackerswork.hsw.service.share;

import com.hackerswork.hsw.persistence.entity.Share;

public interface ShareCommandService {

    Share add(Long personId, Share share);

    void delete(Long id);

}
