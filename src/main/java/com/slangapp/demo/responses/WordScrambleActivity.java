package com.slangapp.demo.responses;

import com.slangapp.demo.enums.ActivityType;
import com.slangapp.demo.models.Resource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WordScrambleActivity implements Serializable {

    private ActivityType activityType = ActivityType.WORD_SCRAMBLE;
    private List<Character> choices;
    private List<Character> correctAnswer;
    private Boolean correct;
    private List<Resource> resources;
    

    public void setLetters(String word){
        if(choices == null){
            choices = new ArrayList<>();
            correctAnswer = new ArrayList<>();
        }
        for(int i=0; i< word.length(); i++){
            choices.add(word.charAt(i));
            correctAnswer.add(word.charAt(i));
        }
        Collections.shuffle(choices);
    }

}
