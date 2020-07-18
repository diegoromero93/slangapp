package com.slangapp.demo.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class WordScrambleActivity extends ActivityAbstract{
    private List<String> choices;
    @JsonProperty("correct_answer")
    private List<String> correctAnswer;
    @JsonProperty("correct_answer_length")
    private int correctAnswerLength;
}