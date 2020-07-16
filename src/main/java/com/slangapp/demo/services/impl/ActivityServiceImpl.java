package com.slangapp.demo.services.impl;

import com.slangapp.demo.enums.ActivityTypeEnum;
import com.slangapp.demo.enums.ResourceTypeEnum;
import com.slangapp.demo.models.PhonemeDistractor;
import com.slangapp.demo.models.Resource;
import com.slangapp.demo.models.Word;
import com.slangapp.demo.pojos.Activity;
import com.slangapp.demo.pojos.WordScrambleActivity;
import com.slangapp.demo.repositories.PhonemeDistractorRepository;
import com.slangapp.demo.repositories.ResourceRepository;
import com.slangapp.demo.repositories.WordRepository;
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

    private HashMap<String, String> distractorsHM;


    @PostConstruct
    public void init(){
        List<PhonemeDistractor> phonemeDistractorList = phonemeDistractorRepository.findAll();
         distractorsHM = new HashMap<>();
        for (PhonemeDistractor distractor : phonemeDistractorList) {
            distractorsHM.put(distractor.getPhoneme(), distractor.getDistractor());
        }
    }

    /**
     * Will generate an activity for the user
     * @param currentActivity
     * @return Activity
     */
    @Override
    public Activity getUserActivity(Activity currentActivity) {
        WordScrambleActivity wordScrambleActivity = new WordScrambleActivity();
        if(currentActivity == null){
            Word word = wordRepository.findById(1l).get();
            List<Resource> resourceList = resourceRepository.findAllByResourceTypeAndWord(ResourceTypeEnum.AUDIO.getCode(),word);
            wordScrambleActivity.setActivityTypeEnum(ActivityTypeEnum.WORD_SCRAMBLE);
            wordScrambleActivity.setChoices(getScrambleWord(word));
            wordScrambleActivity.setResources(resourceList);
            wordScrambleActivity.setActivityId(word.getId());

        }
        return wordScrambleActivity;
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
        for(String currentKey : distractorsHM.keySet()) {
            if((lettersToAdd + wordString.length()) == choices.size()) break;
            if(word.getPhonetic().contains(currentKey)){
                String[] distractors = distractorsHM.get(currentKey).split(",");
                int index = 0;
                if(distractors.length > 1){
                    index = ThreadLocalRandom.current().nextInt(0, distractors.length);
                }
                choices.add(distractors[index]);
            }
        }
        if((lettersToAdd + wordString.length()) > choices.size()){
            do{
                choices.add(getRandomCharacter(choices));
            } while ((lettersToAdd + wordString.length()) > choices.size());

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
