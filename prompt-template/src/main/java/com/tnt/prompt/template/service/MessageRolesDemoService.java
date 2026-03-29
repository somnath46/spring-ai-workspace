package com.tnt.prompt.template.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class MessageRolesDemoService {

    private final ChatClient chatClient;


    private static final String CLAIM_DETAILS = """
            Claim details:
            Policy: BASIC
            Max Coverage: 20000
            Claim Amount: 50000
            """;

    public MessageRolesDemoService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String guideMe(String topic , String level , int points) {
        return chatClient
                .prompt()
                .system("You are a tech assistant." +
                " give best answer to the students and make sure your answer will be to the point.")
                .user("Explain me "+ topic +" in "+ level +" level with" + points + "  points")
                .call()
                .content();
    }

}
