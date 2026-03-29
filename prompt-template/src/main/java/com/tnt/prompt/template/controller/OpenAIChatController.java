package com.tnt.prompt.template.controller;

import com.tnt.prompt.template.service.OpenAIChatService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class OpenAIChatController {

    private final OpenAIChatService openAIChatService;

    public OpenAIChatController(OpenAIChatService openAIChatService) {
        this.openAIChatService = openAIChatService;
    }

    @GetMapping("/chat-option/chat")
    public String chatWithOptions(@RequestParam String message) {
        return openAIChatService.askToAIWithChatOption(message);
    }

    // produces = MediaType.TEXT_EVENT_STREAM_VALUE -> it is for UI friendly response
    @GetMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatWithStreamResponse(@RequestParam String message) {
        return openAIChatService.askToAIWithStreamingResponse(message);
    }

    @GetMapping("/chat-memory/chat")
    public String chatWithOptions(@RequestParam String message, @RequestParam String username) {
        return openAIChatService.askToAIWithChatMemory(message, username);
    }

}
