package com.tnt.prompt.template.service;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;
@Service
public class OpenAIChatService {

//    @Autowired
    private final ChatClient chatClient;
    private final ChatClient chatClientWithMemory;

    public OpenAIChatService(ChatClient.Builder chatClientBuilder, ChatClient chatClientWithMemory) {

        this.chatClient = chatClientBuilder.build();
        this.chatClientWithMemory = chatClientWithMemory;
    }

//    AIzaSyAi9ZtkEodac2jKXgdu3q_M9XJEq_aLHns
    public String getChatOutput(String prompt) {
        return chatClient.prompt(prompt).call().content();
    }

    public String askToAIWithChatOption(String message) {
        final var chatOptions = ChatOptions.builder()
                .model("gpt-4o-mini")
                // stop response when max token is over
                .maxTokens(100)
                .temperature(0.3)
                /*the following methods are not recommended to override - use default provided by spring*/
                // stop repetitive word in response
                .frequencyPenalty(0.7)
                // stop repetitive idea in response
                // higher value stop repetition
                .presencePenalty(0.7)
                // stop while getting this value
                .stopSequences(List.of("}"))
                // no percentage item in response
                .topP(0.5)
                .build();

        return chatClient
                .prompt(message)
                .options(chatOptions)
                .call()
                .content();
    }

    public Flux<String> askToAIWithStreamingResponse(String message) {
        return chatClient
                .prompt(message)
                .stream()
                .content();
    }

    public String askToAIWithChatMemory(String message, String username) {
        return chatClientWithMemory
                .prompt(message)
                .advisors(adviceSpec ->
                        adviceSpec.param(CONVERSATION_ID, username)
                )
                .call()
                .content();
    }
}
