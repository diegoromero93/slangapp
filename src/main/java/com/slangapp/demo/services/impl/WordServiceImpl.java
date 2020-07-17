package com.slangapp.demo.services.impl;

import com.slangapp.demo.controllers.request.WordRequest;
import com.slangapp.demo.controllers.responses.ResourceResponse;
import com.slangapp.demo.controllers.responses.WordResponse;
import com.slangapp.demo.enums.ResourceTypeEnum;
import com.slangapp.demo.models.Resource;
import com.slangapp.demo.models.Word;
import com.slangapp.demo.repositories.ResourceRepository;
import com.slangapp.demo.repositories.WordRepository;
import com.slangapp.demo.services.AmazonAudioManagementService;
import com.slangapp.demo.services.ReadDictionaryService;
import com.slangapp.demo.services.WordService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
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

    @Autowired
    private ModelMapper modelMapper;


    @Override
    @Transactional
    public void saveAll(List<Word> words) throws IOException, ExecutionException, InterruptedException {
        List<Resource> resourceList = new ArrayList<>();
        for(Word word: words){
            word.setPhonetic(getIPAPhonetic(word.getWord()));
            Resource resource = new Resource();
            resource.setWord(word);
            resource.setResourceType(ResourceTypeEnum.AUDIO);
            resource.setResourceUrl(getResourceURL(word.getWord()));
            resourceList.add(resource);
        }
        wordRepository.saveAll(words);
        resourceRepository.saveAll(resourceList);
    }

    @Override
    @Transactional
    public WordResponse save(WordRequest wordRequest) throws IOException, ExecutionException, InterruptedException {
        Word word = getModelFromRequest(wordRequest);
        word.setPhonetic(getIPAPhonetic(word.getWord()));
        Resource resource = new Resource();
        resource.setWord(word);
        resource.setResourceType(ResourceTypeEnum.AUDIO);
        resource.setResourceUrl(getResourceURL(word.getWord()));
        wordRepository.save(word);
        resourceRepository.save(resource);
        return getRequestFromModel(word, resource);
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


    private Word getModelFromRequest(WordRequest wordRequest){
        return modelMapper.map(wordRequest, Word.class);
    }

    private WordResponse getRequestFromModel(Word word, Resource resource){
        WordResponse wordResponse =  modelMapper.map(word, WordResponse.class);
        ResourceResponse resourceResponse = modelMapper.map(resource, ResourceResponse.class);
        wordResponse.setResources(new HashSet<>(Arrays.asList(resourceResponse)));
        return wordResponse;
    }


    private String getResourceURL(String word) throws IOException, ExecutionException, InterruptedException {
        return amazonAudioManagementService.createAndSaveAudioFile(word).get();
    }

    private String getIPAPhonetic(String word) throws FileNotFoundException, ExecutionException, InterruptedException {
        return readDictionaryService.getIPAPhonetic(word).get();
    }

}
