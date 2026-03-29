package com.tnt.multi.model.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MultiModelChatService {

    private final ChatClient openAIChatClient;
    private final ChatClient googleChatClient;

    public MultiModelChatService(ChatClient openAIChatClient, ChatClient googleChatClient) {
        this.openAIChatClient = openAIChatClient;
        this.googleChatClient = googleChatClient;
    }

    public String chatWithOpenAI(String message) {
        return openAIChatClient
                .prompt(message)
                .call()
                .content();
    }

    public String chatWithGoogleGenAI(String message) {
        return googleChatClient
                .prompt(message)
                .call()
                .content();
    }
//    public String chatWithOllama(String message) {
//        return ollamaChatClient
//                .prompt(message)
//                .call()
//                .content();
//    }
}