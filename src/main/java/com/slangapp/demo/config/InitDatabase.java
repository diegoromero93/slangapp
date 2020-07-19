package com.slangapp.demo.config;

import com.slangapp.demo.models.PhonemeDistractor;
import com.slangapp.demo.models.Word;
import com.slangapp.demo.repositories.PhonemeDistractorRepository;
import com.slangapp.demo.services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitDatabase implements ApplicationRunner {

    @Autowired
    WordService wordService;

    @Autowired
    PhonemeDistractorRepository phonemeDistractorRepository;

    @Value("${spring.jpa.hibernate.populate:false}")
    boolean populate;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if(populate){
            List<PhonemeDistractor> phonemeDistractorsList = addPhonemes();
            List<Word> wordList = addSampleWords();
            wordService.saveAll(wordList);
            phonemeDistractorRepository.saveAll(phonemeDistractorsList);
        }

    }
    /**
     *
     * I've map the most common phonemes mistakes, this can be done with more time and in a better way
     * Sources:
     * + https://www.englishclub.com/pronunciation/minimal-pairs.htm
     * + https://allesl.com/minimal-pairs-list-examples/
     * + https://glossary.sil.org/term/phonetically-similar-segment#:~:text=Phonetically%20similar%20segments%20are%20two,one%20or%20two%20articulatory%20features.
     *
     * @return List<PhonemeDistractor>
     */
    private List<PhonemeDistractor> addPhonemes(){
        String distractors = "ɪ-ea|i-i|ɛ-i,ai,a|ɪ-e|eɪ-e|æ-u|ʌ-a|oʊ-a|ɔ-o|æ-e|b-v,p|v-b|p-b|n-g|ŋ-n|l-r|r-l|s-h,th|f-v,h,th|v-f|h-f|θ-f,s";
        List<PhonemeDistractor> phonemeDistractorsList = new ArrayList<>();
        String[] distractorsArray = distractors.split("\\|");
        for (String s : distractorsArray) {
            PhonemeDistractor phonemeDistractor = new PhonemeDistractor();
            String[] item = s.split("-");
            phonemeDistractor.setPhoneme(item[0]);
            phonemeDistractor.setDistractor(item[1]);
            phonemeDistractorsList.add(phonemeDistractor);
        }
        return phonemeDistractorsList;
    }

    /**
     * Will add Sample words to the database
     * @return List<Word>
     */
    private List<Word> addSampleWords(){
        String nouns = "sit, seat, desk, disk, wet, wait, bat, but, book, so, saw, time, person, year, way, day, thing, man, world, life, hand, part, child, eye, woman, place, work, week, case, point, government, company, number, group, problem, fact";
        String[] nounsArray = nouns.split(",");
        List<Word> wordList = new ArrayList<>();
        for(int i = 0; i <nounsArray.length; i++){
            Word word = new Word();
            word.setWord(nounsArray[i].trim());
            wordList.add(word);
        }
        return wordList;
    }
}
