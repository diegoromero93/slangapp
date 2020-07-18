package com.slangapp.demo.repositories;

import com.slangapp.demo.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>, PagingAndSortingRepository<Activity, Long> {
}
