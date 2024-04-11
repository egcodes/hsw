package com.hackerupdates.hsw.domain.repository;

import com.hackerupdates.hsw.domain.dto.ActivityDTO;
import com.hackerupdates.hsw.domain.entity.Activity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Modifying
    @Query("UPDATE Activity a SET a.lastActivityTime = :timestamp  WHERE a.personId = :personId")
    void updateLastActivityTimeByPersonId(Long personId, Long timestamp);

    @Query("SELECT new com.hackerupdates.hsw.domain.dto.ActivityDTO(p.id, p.userName, p.name, a.lastActivityTime) FROM Person p LEFT JOIN Activity a "
        + "ON p.id = a.personId "
        + "WHERE a.personId IN :personIds")
    List<ActivityDTO> findAllByPersonIds(List<Long> personIds);
}
