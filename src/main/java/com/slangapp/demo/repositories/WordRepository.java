package com.slangapp.demo.repositories;

import com.slangapp.demo.models.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, Long>, PagingAndSortingRepository<Word, Long> {
}
