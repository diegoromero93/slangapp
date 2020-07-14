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
public class WordScrambleActivity implements Serializable, Activity {

    List<Character> letters;
    ActivityType activityType = ActivityType.WORD_SCRAMBLE;
    List<Resource> resources;
    

    public void setLetters(String word){
        if(letters == null){
            letters = new ArrayList<>();
        }
        for(int i=0; i< word.length(); i++){
            letters.add(word.charAt(i));
        }
        Collections.shuffle(letters);
    }

    @Override
    public Double calculateXP() {
        return null;
    }
}
