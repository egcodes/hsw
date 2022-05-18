package com.hackerswork.hsw.persistence.repository;

import com.hackerswork.hsw.persistence.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

}
