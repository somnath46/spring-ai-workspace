package com.tnt.open.ai.demo.service;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenAIChatService {

//    @Autowired
    private final ChatClient chatClient;

    public OpenAIChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

//    AIzaSyAi9ZtkEodac2jKXgdu3q_M9XJEq_aLHns
    public String getChatOutput(String prompt) {
        return chatClient.prompt(prompt).call().content();
    }
}
