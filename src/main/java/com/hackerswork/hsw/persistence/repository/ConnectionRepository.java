package com.hackerswork.hsw.persistence.repository;

import com.hackerswork.hsw.dto.ConnectionDTO;
import com.hackerswork.hsw.dto.PersonSumDTO;
import com.hackerswork.hsw.enums.Status;
import com.hackerswork.hsw.persistence.entity.Connection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    Connection findByPersonIdAndConnectionId(Long personId, Long connectionId);

    @Query("SELECT new com.hackerswork.hsw.dto.ConnectionDTO(c.connectionId, c.pinned) "
        + "FROM Connection c WHERE c.personId = :personId AND c.blocked = false AND c.hidden = false")
    List<ConnectionDTO> findAllByPersonId(Long personId);

    @Query("SELECT c.connectionId FROM Connection c LEFT JOIN Person p ON c.connectionId = p.id"
        + " WHERE p.status = :status AND c.blocked = false AND c.hidden = false AND c.personId = :personId")
    List<Long> findConnectionsByPersonId(Long personId, Status status);

    int countByConnectionId(Long connectionId);

    @Query("SELECT new com.hackerswork.hsw.dto.PersonSumDTO(p.id, p.userName, p.name)  FROM Connection c LEFT JOIN Person p ON c.personId = p.id"
        + " WHERE p.status = :status AND c.blocked = false AND c.connectionId = :personId")
    List<PersonSumDTO> findNameByConnectionId(Long personId, Status status);

    @Query("SELECT new com.hackerswork.hsw.dto.PersonSumDTO(p.id, p.userName, p.name) FROM Connection c LEFT JOIN Person p ON c.connectionId = p.id"
        + " WHERE p.status = :status AND c.blocked = false AND c.personId = :personId")
    List<PersonSumDTO> findNameByPersonId(Long personId, Status status);

    @Query("SELECT new com.hackerswork.hsw.dto.PersonSumDTO(p.id, p.userName, p.name) FROM Connection c LEFT JOIN Person p ON c.connectionId = p.id"
        + " WHERE c.blocked = true AND p.status = :status AND c.personId = :personId")
    List<PersonSumDTO> findNameByPersonIdAndBlocked(Long personId, Status status);

    @Query("SELECT new com.hackerswork.hsw.dto.PersonSumDTO(p.id, p.userName, p.name) FROM Connection c LEFT JOIN Person p ON c.connectionId = p.id"
        + " WHERE c.hidden = true AND p.status = :status AND c.personId = :personId")
    List<PersonSumDTO> findNameByPersonIdAndHidden(Long personId, Status status);

}
