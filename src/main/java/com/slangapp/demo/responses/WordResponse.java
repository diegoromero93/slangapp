package com.slangapp.demo.responses;

import com.slangapp.demo.models.Resource;
import com.slangapp.demo.models.Word;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class WordResponse implements Serializable {
    private Word word;
    private List<Resource> resourceList;
}
