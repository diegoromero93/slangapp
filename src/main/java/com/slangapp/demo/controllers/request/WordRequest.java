package com.slangapp.demo.controllers.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordRequest implements Serializable {
    @NotNull
    @NotBlank
    @NotEmpty
    private String word;
}
