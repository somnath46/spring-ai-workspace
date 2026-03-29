package com.tnt.prompt.template.controller;

import com.tnt.prompt.template.service.MessageRolesDemoService;
import com.tnt.prompt.template.service.OpenAIChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prompt-template/api/ai")
public class PromptTemplateChatController {

    private final OpenAIChatService chatService;
    private final MessageRolesDemoService messageRolesDemoService;

    public PromptTemplateChatController(OpenAIChatService chatService, MessageRolesDemoService messageRolesDemoService) {
        this.chatService = chatService;
        this.messageRolesDemoService = messageRolesDemoService;
    }

    // simple chat
    @GetMapping("/chat")
    public String chat(@RequestParam String prompt) {
        return chatService.getChatOutput(prompt);
    }

    // chat guides or inform about specific topic, user's expertise level, number of points
    // service has a template where these inputs are handled as a parameter
    @GetMapping("/guide")
    public String guideUser(@RequestParam String topic , @RequestParam String level, @RequestParam int point) {
        return messageRolesDemoService.guideMe(topic, level, point);
    }
}
