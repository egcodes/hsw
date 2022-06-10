package com.hackerswork.hsw.persistence.repository;

import com.hackerswork.hsw.dto.ShareDTO;
import com.hackerswork.hsw.persistence.entity.Share;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {

    @Query("SELECT new com.hackerswork.hsw.dto.ShareDTO(s.id, p.userName, p.name, s.text, s.createdTime) FROM Person p LEFT JOIN Share s "
        + "ON p.id = s.personId "
        + "WHERE s.personId IN :personIds "
        + "ORDER BY s.createdTime DESC")
    List<ShareDTO> findAllByPersonIds(List<Long> personIds, Pageable pageable);

    @Query("SELECT new com.hackerswork.hsw.dto.ShareDTO(s.id, p.userName, p.name, s.text, s.createdTime) FROM Person p LEFT JOIN Share s "
        + "ON p.id = s.personId "
        + "WHERE s.id > :offset AND s.personId IN :personIds "
        + "ORDER BY s.createdTime DESC")
    List<ShareDTO> findByOffsetAndPersonIds(List<Long> personIds, Long offset);
}
