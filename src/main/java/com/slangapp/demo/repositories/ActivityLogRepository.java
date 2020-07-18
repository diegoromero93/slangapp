package com.slangapp.demo.repositories;

import com.slangapp.demo.models.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long>, PagingAndSortingRepository<ActivityLog, Long> {
}
