package com.slangapp.demo.services;

import java.io.FileNotFoundException;
import java.util.concurrent.CompletableFuture;

public interface ReadDictionaryService {
    CompletableFuture<String> getIPAPhonetic(String word) throws FileNotFoundException;
}
