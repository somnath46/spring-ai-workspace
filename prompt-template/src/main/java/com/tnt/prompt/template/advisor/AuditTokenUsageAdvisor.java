package com.tnt.prompt.template.advisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.model.ChatResponse;

public class AuditTokenUsageAdvisor implements CallAdvisor {

    Logger logger = LoggerFactory.getLogger(AuditTokenUsageAdvisor.class);

    private final String name;
    private final int order;

    public AuditTokenUsageAdvisor(String name, int order) {
        this.name = name;
        this.order = order;
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        // call next advisor or AI model - LLM
        // something like Filter
        ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);

        ChatResponse chatResponse = chatClientResponse.chatResponse();

        // check audit token form metadat part
        if (chatResponse != null) {
            final var usage = chatResponse.getMetadata().getUsage();

            if (usage != null) {
                final var inputToken = usage.getPromptTokens();
                final var outputToken = usage.getCompletionTokens();
                final var totalTokens = usage.getTotalTokens();

                //log those details
                logger.info("Token Usage - input Tokens: {}, output Tokens: {}, Total Tokens: {}",
                        inputToken, outputToken, totalTokens);
            }
        }
        return chatClientResponse;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getOrder() {
        return order;
    }
}
