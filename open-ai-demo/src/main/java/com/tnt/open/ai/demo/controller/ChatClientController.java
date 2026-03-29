package com.tnt.open.ai.demo.controller;

import com.tnt.open.ai.demo.service.OpenAIChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class ChatClientController {

    @Autowired
    OpenAIChatService chatService;


    @GetMapping("/chat")
    public String chat(@RequestParam String prompt) {
        return chatService.getChatOutput(prompt);
    }


}
