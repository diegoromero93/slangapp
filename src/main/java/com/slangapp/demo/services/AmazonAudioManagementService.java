package com.slangapp.demo.services;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface AmazonAudioManagementService {

    CompletableFuture<String> createAndSaveAudioFile(String word) throws IOException;

}
