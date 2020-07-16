package com.slangapp.demo;

import com.slangapp.demo.models.Word;
import com.slangapp.demo.services.WordService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    CommandLineRunner initDatabase(WordService wordService) {
        String distractors = "ɪ-ea|i-i|ɛ-i|ɪ-e|ɛ-ai|eɪ-e|æ-u|ʌ-a|oʊ-a|ɔ-o|æ-e|ɛ-a|b-v,p|v-b|p-b|n-g|ŋ-n|l-r|r-l|s-h,th|f-v,h,t|v-f|h-f|θ-f,s";
        String[] distractorsArray = distractors.split("\\|");
        HashMap<String, String> distractorsHM = new HashMap<>();
        for (String s : distractorsArray) {
            String[] item = s.split("-");
            distractorsHM.put(item[0], item[1]);
        }
        try {
            File file = new ClassPathResource("static/CMU-ipa-syllables.txt").getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String nouns = "time, person, year, way, day, thing, man, world, life, hand, part, child, eye, woman, place, work, week, case, point, government, company, number, group, problem, fact";
        String[] nounsArray = nouns.split(",");
        List<Word> wordList = new ArrayList<>();
        for(int i = 0; i <nounsArray.length; i++){
            Word word = new Word();
            word.setWord(nounsArray[i].trim());
            wordList.add(word);
        }

        return args -> {
            wordService.saveAll(wordList);
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
}
