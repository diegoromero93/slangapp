package com.slangapp.demo.repositories;

import com.slangapp.demo.models.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long>, PagingAndSortingRepository<Word, Long> {

    @Query(value = "SELECT w FROM Word w WHERE (:word is null or lower(w.word) LIKE lower(concat('%', :word,'%')))")
    Page<Word> findByFilterName(Pageable page , @Param("word") String word);

    Word findFirstByIdGreaterThanEqualOrderById(long id);

    Word findFirstByIdGreaterThanOrderById(long id);
}
