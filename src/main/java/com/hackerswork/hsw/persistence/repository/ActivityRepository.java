package com.hackerswork.hsw.persistence.repository;

import com.hackerswork.hsw.dto.ActivityDTO;
import com.hackerswork.hsw.persistence.entity.Activity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Activity findByPersonId(Long personId);

    @Modifying
    @Query("UPDATE Activity a SET a.lastActivityTime = :timestamp  WHERE a.personId = :personId")
    void updateLastActivityTimeByPersonId(Long personId, Long timestamp);

    @Query("SELECT new com.hackerswork.hsw.dto.ActivityDTO(p.userName, p.name, a.lastActivityTime) FROM Person p LEFT JOIN Activity a "
        + "ON p.id = a.personId "
        + "WHERE a.personId IN :personIds "
        + "ORDER BY a.lastActivityTime DESC")
    List<ActivityDTO> findAllByPersonIds(List<Long> personIds);
}
