package com.slangapp.demo;

import com.slangapp.demo.enums.ResourceType;
import com.slangapp.demo.models.Resource;
import com.slangapp.demo.models.Word;
import com.slangapp.demo.repositories.ResourceRepository;
import com.slangapp.demo.repositories.WordRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.CommandLineRunner;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
@EntityScan(basePackages = { "com.slangapp.demo.models" })
@EnableJpaRepositories("com.slangapp.demo.repositories")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Bean
    CommandLineRunner initDatabase(WordRepository wordRepository, ResourceRepository resourceRepository) {
        String nouns = "time, person, year, way, day, thing, man, world, life, hand, part, child, eye, woman, place, work, week, case, point, government, company, number, group, problem, fact";
        String[] nounsArray = nouns.split(",");
        List<Word> wordList = new ArrayList<>();
        List<Resource> resourceList = new ArrayList<>();
        for(int i = 0; i <nounsArray.length; i++){
            Word word = new Word();
            word.setWord(nounsArray[i].trim());
            wordList.add(word);
            Resource resource = new Resource();
            resource.setWord(word);
            resource.setResourceType(ResourceType.AUDIO);
            resourceList.add(resource);
        }

        return args -> {
            wordRepository.saveAll(wordList);
            resourceRepository.saveAll(resourceList);
        };
    }

}
