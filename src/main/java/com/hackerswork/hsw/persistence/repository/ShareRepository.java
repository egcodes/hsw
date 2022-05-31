package com.hackerswork.hsw.persistence.repository;

import com.hackerswork.hsw.persistence.entity.Share;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {

    List<Share> findByPersonId(Long personId);

}
