package com.slangapp.demo.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "word")
@EqualsAndHashCode(of = { "id" })
@ToString(of = { "id" })
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Word implements Serializable {

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id", nullable = false)
    private Long id;

    @Column(name = "word", nullable = false)
    private String word;

    @Column(name = "phonetic", nullable = false)
    private String phonetic;

    public void setWord(String word){
        this.word = word.toLowerCase().trim();
    }

}
