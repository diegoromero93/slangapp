package com.slangapp.demo.services.impl;

import com.slangapp.demo.config.exceptions.MissingAnswerException;
import com.slangapp.demo.controllers.request.ActivityEvaluationRequest;
import com.slangapp.demo.controllers.responses.ResourceResponse;
import com.slangapp.demo.enums.ActivityTypeEnum;
import com.slangapp.demo.enums.ResourceTypeEnum;
import com.slangapp.demo.models.PhonemeDistractor;
import com.slangapp.demo.models.Resource;
import com.slangapp.demo.models.Word;
import com.slangapp.demo.pojos.Activity;
import com.slangapp.demo.pojos.ActivityAbstract;
import com.slangapp.demo.pojos.WordScrambleActivity;
import com.slangapp.demo.repositories.PhonemeDistractorRepository;
import com.slangapp.demo.repositories.ResourceRepository;
import com.slangapp.demo.repositories.WordRepository;
import com.slangapp.demo.controllers.responses.ActivityResponse;
import com.slangapp.demo.services.ActivityService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
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

    @Autowired
    private ModelMapper modelMapper;

    private List<PhonemeDistractor> phonemeDistractorList;

    @PostConstruct
    public void init(){ phonemeDistractorList = phonemeDistractorRepository.findAll(); }

    @Override
    public ActivityResponse getUserInitialActivity() { return getUserActivity(1l); }

    /**
     * Will generate an activity for the user
     * @param id
     * @return Activity
     */
    @Override
    public ActivityResponse getUserActivity(Long id) {
        Activity currentActivity =  getActivityByWordId(id);
        Activity nextActivity = null;
        if(currentActivity != null && currentActivity instanceof ActivityAbstract){
            nextActivity = getNextActivityByWordId(((ActivityAbstract) currentActivity).getActivityId());
        }

        return ActivityResponse.builder().currentActivity(currentActivity).nextActivity(nextActivity).build();

    }

    @Override
    public ActivityResponse evaluate(ActivityEvaluationRequest actEvRequest) throws MissingAnswerException{
        Word currentWord = wordRepository.getOne(actEvRequest.getCurrentActivityId());
        if(currentWord.getWord().length() > actEvRequest.getAnswer().size()  ){
            throw new MissingAnswerException();
        }


        return evaluateResponse(actEvRequest);
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
        // getting the distractors by phoneme
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

    /**
     * Method to get the current activity
     * @param wordId
     * @return
     */
    private Activity getActivityByWordId(long wordId){
        Word word = wordRepository.findFirstByIdGreaterThanEqualOrderById(wordId);
        return getActivityByWord(word);
    }

    /**
     * I did this second method because we can have more activity types, and add mor logic here
     * @param wordId
     * @return
     */
    private Activity getNextActivityByWordId(long wordId){
        Word word = wordRepository.findFirstByIdGreaterThanOrderById(wordId);
        return getActivityByWord(word);
    }

    /**
     * Method to get the Activity By Word
     * @param word
     * @return
     */
    private Activity getActivityByWord(Word word){
        if(word == null) return null;
        return WordScrambleActivity.builder()
                .activityTypeEnum(ActivityTypeEnum.WORD_SCRAMBLE)
                .correctAnswerLength(word.getWord().length())
                .choices(getScrambleWord(word))
                .resources(getResources(word, ActivityTypeEnum.WORD_SCRAMBLE))
                .activityId(word.getId()).build();
    }

    /**
     * Get as Response Type the list of resources needed, ready to add more activity types
     * @param word
     * @return
     */
    private List<ResourceResponse> getResources(Word word, ActivityTypeEnum activityTypeEnum){
        List<Resource> resourceList = new ArrayList<>();
        switch (activityTypeEnum){
            case WORD_SCRAMBLE:
                resourceList = resourceRepository.findAllByResourceTypeAndWord(ResourceTypeEnum.AUDIO.getCode(),word);
                break;
            default:
                    break;
        }

        Type targetListType = new TypeToken<List<ResourceResponse>>() {}.getType();
        return modelMapper.map(resourceList, targetListType);
    }

    private ActivityResponse evaluateResponse(ActivityEvaluationRequest activityEvaluationRequest){


        return null;

    }
}
