package com.slangapp.demo;

import com.slangapp.demo.models.PhonemeDistractor;
import com.slangapp.demo.models.Word;
import com.slangapp.demo.repositories.PhonemeDistractorRepository;
import com.slangapp.demo.services.WordService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;


@SpringBootApplication
@EnableAsync
@EntityScan(basePackages = { "com.slangapp.demo.models" })
@EnableJpaRepositories("com.slangapp.demo.repositories")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Bean
    CommandLineRunner initDatabase(WordService wordService, PhonemeDistractorRepository phonemeDistractorRepository) {
        List<PhonemeDistractor> phonemeDistractorsList = addPhonemes();
        List<Word> wordList = addSampleWords();

        return args -> {
            //wordService.saveAll(wordList);
            //phonemeDistractorRepository.saveAll(phonemeDistractorsList);
        };
    }


    @Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("SlangAppThread-");
        executor.initialize();
        return executor;
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
        String distractors = "ɪ-ea|i-i|ɛ-i|ɪ-e|ɛ-ai|eɪ-e|æ-u|ʌ-a|oʊ-a|ɔ-o|æ-e|ɛ-a|b-v,p|v-b|p-b|n-g|ŋ-n|l-r|r-l|s-h,th|f-v,h,t|v-f|h-f|θ-f,s";
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
        String nouns = "book, time, person, year, way, day, thing, man, world, life, hand, part, child, eye, woman, place, work, week, case, point, government, company, number, group, problem, fact";
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
