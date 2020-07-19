package com.slangapp.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slangapp.demo.controllers.WordController;
import com.slangapp.demo.controllers.request.WordRequest;
import com.slangapp.demo.controllers.responses.WordResponse;
import com.slangapp.demo.services.WordService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@WebMvcTest(WordController.class)
public class WordTest {

    @MockBean
    private WordService wordService;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;



    @Value("${aws.header.validation}")
    String header;


    @Test
    public void createWordWithOutHeader() throws Exception {
        WordRequest wordRequest = WordRequest.builder().word("medicine").build();
        mockMvc.perform(post("/api/v1/word")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(wordRequest)))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void createWord() throws Exception {
        WordRequest wordRequest = WordRequest.builder().word("medicine").build();
        mockMvc.perform(post("/api/v1/word")
                .contentType("application/json")
                .header("validation-header", header)
                .content(objectMapper.writeValueAsString(wordRequest)))
                .andExpect(status().isCreated());
    }


    @Test
    public void findWord() throws Exception {
        WordResponse wordResponse = new WordResponse();
        when(wordService.save(any(WordRequest.class))).thenReturn(wordResponse);
        mockMvc.perform(get("/api/v1/word/{word_id}", wordResponse.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }


}
