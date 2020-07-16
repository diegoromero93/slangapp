package com.slangapp.demo.services.impl;

import com.slangapp.demo.enums.ResourceTypeEnum;
import com.slangapp.demo.models.Resource;
import com.slangapp.demo.models.Word;
import com.slangapp.demo.repositories.ResourceRepository;
import com.slangapp.demo.repositories.WordRepository;
import com.slangapp.demo.services.AmazonAudioManagementService;
import com.slangapp.demo.services.ReadDictionaryService;
import com.slangapp.demo.services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class WordServiceImpl implements WordService {


    @Autowired
    AmazonAudioManagementService amazonAudioManagementService;

    @Autowired
    WordRepository wordRepository;

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    ReadDictionaryService readDictionaryService;


    @Override
    public List<Word> saveAll(List<Word> words) throws IOException, ExecutionException, InterruptedException {
        List<Resource> resourceList = new ArrayList<>();
        for(Word word: words){
            String resourceURL ="";// amazonAudioManagementService.createAndSaveAudioFile(word.getWord()).get();
            String phonetic = readDictionaryService.getIPAPhonetic(word.getWord()).get();
            word.setPhonetic(phonetic);
            Resource resource = new Resource();
            resource.setWord(word);
            resource.setResourceType(ResourceTypeEnum.AUDIO);
            resource.setResourceUrl(resourceURL);
            resourceList.add(resource);
        }
        List<Word> wordList = wordRepository.saveAll(words);
        resourceRepository.saveAll(resourceList);
        return wordList;
    }

    @Override
    public Word save(Word word) throws IOException, ExecutionException, InterruptedException {
        String resourceURL = "";//amazonAudioManagementService.createAndSaveAudioFile(word.getWord()).get();
        String phonetic = readDictionaryService.getIPAPhonetic(word.getWord()).get();
        word.setPhonetic(phonetic);
        Resource resource = new Resource();
        resource.setWord(word);
        resource.setResourceType(ResourceTypeEnum.AUDIO);
        resource.setResourceUrl(resourceURL);
        Word newWord = wordRepository.save(word);
        resourceRepository.save(resource);
        return newWord;
    }

    @Override
    public Page<Word> getAllWords(Integer pageNo, Integer itemsPerPage, String[] sortBy, String[] desc, String word) {
        List<Sort.Order> orders = new ArrayList<>();
        if(sortBy.length > 0 && desc.length > 0){
            for(int i=0;i < sortBy.length ; i++){
                if("false".equals(desc[i])){
                    orders.add(new Sort.Order(Sort.Direction.ASC, sortBy[i]));
                } else {
                    orders.add(new Sort.Order(Sort.Direction.DESC, sortBy[i]));
                }
            }
        }
        Pageable pageable = PageRequest.of(pageNo, itemsPerPage, Sort.by(orders));
        Page<Word> pagedResult = wordRepository.findByFilterName(pageable, word);
        return pagedResult;
    }

}
