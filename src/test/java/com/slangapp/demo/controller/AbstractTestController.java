package com.slangapp.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Abstract class to expose the most common elements used
 * while testing Controller Classes.
 * This as well has the goal of reducing the number of
 * application context that could be load and reduce testing time.
 */
@Getter
@AutoConfigureRestDocs
@ActiveProfiles("test")
public abstract class AbstractTestController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

}
