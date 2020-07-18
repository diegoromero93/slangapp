package com.slangapp.demo.repositories;

import com.slangapp.demo.models.Resource;
import com.slangapp.demo.models.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long>, PagingAndSortingRepository<Resource, Long> {
    List<Resource> findAllByResourceTypeAndWord(String resourceType, Word word);
    List<Resource> findByWord(Word word);
}
