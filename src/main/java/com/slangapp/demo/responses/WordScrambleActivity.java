package com.slangapp.demo.responses;

import com.slangapp.demo.enums.ActivityTypeEnum;
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

    private ActivityTypeEnum activityTypeEnum = ActivityTypeEnum.WORD_SCRAMBLE;
    private List<String> choices;
    private List<Character> correctAnswer;
    private Boolean correct;
    private List<Resource> resources;
    private int activityId;
    private int maxQuantity;
    private String word;

    //Minimal Pair
    //https://www.englishclub.com/pronunciation/minimal-pairs.htm
    //https://allesl.com/minimal-pairs-list-examples/
    //https://glossary.sil.org/term/phonetically-similar-segment#:~:text=Phonetically%20similar%20segments%20are%20two,one%20or%20two%20articulatory%20features.
    public void setLetters(String word){
        List<Character> characters = new ArrayList<>();
        if(choices == null){
            choices = new ArrayList<>();
            correctAnswer = new ArrayList<>();
        }
        for(int i=0; i< word.length(); i++){
            char character = Character.toLowerCase(word.charAt(i));
            choices.add(String.valueOf(word.charAt(i)));
            switch (character){
                case 'r':
                    characters.add('l');
                    break;
                case 'l':
                    characters.add('r');
                    break;
                case 'f':
                    characters.add('v');
                    break;
                case 'v':
                    characters.add('f');
                    break;
                case 'p':
                    characters.add('b');
                    characters.add('f');
                    characters.add('t');
                    break;
                case 't':
                    characters.add('p');
                    break;
            }
            //correctAnswer.add(word.charAt(i));
        }
        maxQuantity = word.length();
        Collections.shuffle(choices);
    }

}

//[p] and [b]
//
//voicing.
//
//[p] and [f]
//
//place of articulation and manner of articulation.
//
//[l] and [r]
//
//manner of articulation.
//
//[p] and [t]
//
//place of articulation.
