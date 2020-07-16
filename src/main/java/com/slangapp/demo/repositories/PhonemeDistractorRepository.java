package com.slangapp.demo.repositories;

import com.slangapp.demo.models.PhonemeDistractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhonemeDistractorRepository extends JpaRepository<PhonemeDistractor, Long>, PagingAndSortingRepository<PhonemeDistractor, Long> {
}
