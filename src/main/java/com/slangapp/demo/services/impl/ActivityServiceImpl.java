package com.slangapp.demo.services.impl;

import com.slangapp.demo.enums.ActivityTypeEnum;
import com.slangapp.demo.enums.ResourceTypeEnum;
import com.slangapp.demo.models.PhonemeDistractor;
import com.slangapp.demo.models.Resource;
import com.slangapp.demo.models.Word;
import com.slangapp.demo.pojos.WordScrambleActivity;
import com.slangapp.demo.repositories.PhonemeDistractorRepository;
import com.slangapp.demo.repositories.ResourceRepository;
import com.slangapp.demo.repositories.WordRepository;
import com.slangapp.demo.controllers.responses.ActivityResponse;
import com.slangapp.demo.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private PhonemeDistractorRepository phonemeDistractorRepository;

    private List<PhonemeDistractor> phonemeDistractorList;

    @PostConstruct
    public void init(){
        phonemeDistractorList = phonemeDistractorRepository.findAll();
    }

    /**
     * Will generate an activity for the user
     * @param id
     * @return Activity
     */
    @Override
    public ActivityResponse getUserActivity(Long id) {
        WordScrambleActivity wordScrambleActivity = new WordScrambleActivity();
        Word word = wordRepository.findById(id).get();
        List<Resource> resourceList = resourceRepository.findAllByResourceTypeAndWord(ResourceTypeEnum.AUDIO.getCode(),word);
        wordScrambleActivity.setActivityTypeEnum(ActivityTypeEnum.WORD_SCRAMBLE);
        wordScrambleActivity.setChoices(getScrambleWord(word));
        wordScrambleActivity.setResources(resourceList);
        wordScrambleActivity.setActivityId(word.getId());

        ActivityResponse activityResponse = new ActivityResponse();
        activityResponse.setCurrentActivity(wordScrambleActivity);


        activityResponse.setNextActivities(wordScrambleActivity);
        return activityResponse;
    }

    /**
     * This method will create a scramble list of character from an existing word
     * Will add 20% plus of characters
     * @param word
     * @return List<String>
     */
    private List<String> getScrambleWord(Word word){
        List<String> choices = new ArrayList<>();
        String wordString = word.getWord();
        int lettersToAdd = new Double(wordString.length() * 0.2).intValue();
        lettersToAdd = lettersToAdd == 0 ? 1 : lettersToAdd;
        for(int i=0; i< wordString.length(); i++){
            choices.add(String.valueOf(wordString.charAt(i)));
        }
        for(PhonemeDistractor currentKey : phonemeDistractorList) {
            if(choices.size() >= (lettersToAdd + wordString.length())) break;
            if(word.getPhonetic().contains(currentKey.getPhoneme())){
                String[] distractors = currentKey.getDistractor().split(",");
                int index = 0;
                if(distractors.length > 1){
                    index = ThreadLocalRandom.current().nextInt(0, distractors.length);
                }
                choices.addAll(Arrays.asList(distractors[index].split("")));
            }
        }
        while ((lettersToAdd + wordString.length()) > choices.size()){
            choices.add(getRandomCharacter(choices));
        }
        Collections.shuffle(choices);
        return choices;
    }


    /**
     * Get random character to complete word
     * @param choices
     * @return
     */
    private String getRandomCharacter(List<String> choices){
        Boolean duplicate = new Random().nextBoolean();
        if(duplicate){
            int index = ThreadLocalRandom.current().nextInt(0, choices.size());
            return choices.get(index);
        } else {
            Random r = new Random();
            return String.valueOf((char)(r.nextInt(26) + 'a'));
        }

    }
}
