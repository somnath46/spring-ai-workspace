package com.tnt.prompt.template.service;

import com.tnt.prompt.template.advisor.AuditTokenUsageAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderSupportAIAssistantService {

    @Value("classpath:prompts/order_system_template.st")
    private Resource orderSystemPrompt;

    @Value("classpath:prompts/order_user_template.st")
    private Resource orderUserPrompt;

    @Value("classpath:prompts/order_system_policy.st")
    private Resource orderSystemPolicyPrompt;

    private final ChatClient chatClient;

    public OrderSupportAIAssistantService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String assistWithOrderSupport(String customerName, String orderId, String customerMessage) {
        return chatClient
                .prompt()
                .system(orderSystemPrompt)
                .user(promptuserSpec -> promptuserSpec.text(orderUserPrompt)
                        .param("customerName", customerName)
                        .param("orderId", orderId)
                        .param("customerMessage", customerMessage))
                .call()
                .content();
    }

    /*
    * 1. Template
    * Here we are using a system template with specific policies for different item category.
    * Due to that AI response is as per the given policy
    * Example:
    * customerMessage: I received a different tshirt
    * response:
    * I'm sorry to hear that you received the wrong t-shirt, Sujay. To resolve this issue, we can initiate a replacement for you. Please ensure that you report the incorrect item within 48 hours of delivery.
    * Once confirmed, the replacement will be delivered within 3–5 business days. If you have any questions or need further assistance, please feel free to reach out.
    *
    * 2. Advisors
    * SimpleLoggerAdvisor -> For logging
    * SafeGuardAdvisor -> Restrict any sensitive info before going to LLM
    * AuditTokenUsageAdvisor -> Custom advisor, log token used for the request-response
    *
    * */
    public String talkToAISupport(String customerName, String orderId, String customerMessage) {
        return chatClient
                .prompt()
                .advisors(
                        List.of(new SimpleLoggerAdvisor(),
                                new SafeGuardAdvisor(List.of("password", "otp", "cvv"), // list of words
                                        // failure response
                                        "For security reason, we do not ask any sensitive information, please contact support",
                                        // order
                                        1),
                                new AuditTokenUsageAdvisor("AuditTokenUsageAdvisor", 2))
                )
                .system(orderSystemPolicyPrompt)
                .user(promptuserSpec -> promptuserSpec.text(orderUserPrompt)
                        .param("customerName", customerName)
                        .param("orderId", orderId)
                        .param("customerMessage", customerMessage))
                .call()
                .content();
    }
}
