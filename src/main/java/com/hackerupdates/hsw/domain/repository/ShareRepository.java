package com.hackerupdates.hsw.domain.repository;

import com.hackerupdates.hsw.domain.dto.ShareDTO;
import com.hackerupdates.hsw.domain.entity.Share;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {

    @Query("SELECT new com.hackerupdates.hsw.domain.dto.ShareDTO(s.id, p.userName, p.name, s.text, s.createdTime) FROM Person p LEFT JOIN Share s "
        + "ON p.id = s.personId "
        + "WHERE s.personId IN :personIds "
        + "ORDER BY s.id DESC")
    List<ShareDTO> findAllByPersonIds(List<Long> personIds, Pageable pageable);

    @Query("SELECT new com.hackerupdates.hsw.domain.dto.ShareDTO(s.id, p.userName, p.name, s.text, s.createdTime) FROM Person p LEFT JOIN Share s "
        + "ON p.id = s.personId "
        + "WHERE s.id > :offset AND s.personId IN :personIds "
        + "ORDER BY s.id DESC")
    List<ShareDTO> findByOffsetAndPersonIds(List<Long> personIds, Long offset, Pageable pageable);

    @Query("SELECT new com.hackerupdates.hsw.domain.dto.ShareDTO(s.id, p.userName, p.name, s.text, s.createdTime) FROM Share s LEFT JOIN Person p "
            + "ON p.id = s.personId "
            + "WHERE s.hidden = false "
            + "ORDER BY s.id DESC")
    List<ShareDTO> findAllShares(Pageable pageable);

    @Query("SELECT new com.hackerupdates.hsw.domain.dto.ShareDTO(s.id, p.userName, p.name, s.text, s.createdTime) FROM Share s LEFT JOIN Person p "
            + "ON p.id = s.personId "
            + "WHERE s.id > :offset AND s.hidden = false "
            + "ORDER BY s.id DESC")
    List<ShareDTO> findAllByOffset(Long offset, Pageable pageable);
}
