package com.hackerswork.hsw.persistence.repository;

import com.hackerswork.hsw.persistence.entity.Connection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    @Query("SELECT c.connectionId FROM Connection c WHERE c.personId = :personId")
    List<Long> findConnectionsByPersonId(Long personId);
}
