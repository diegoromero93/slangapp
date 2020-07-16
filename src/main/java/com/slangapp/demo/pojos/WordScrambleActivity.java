package com.slangapp.demo.pojos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WordScrambleActivity extends ActivityAbstract implements Serializable {
    private List<String> choices;
    private List<Character> correctAnswer;
}