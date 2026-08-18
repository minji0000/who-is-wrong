package com.kari.whoiswrong.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiController {

    private final ChatClient chatClient;

    public AiController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/ai")
    public String ai(@RequestParam String message) {

        return chatClient
                .prompt()
                .system("너는 갈등 상황의 시시비비를 가려주는 판정사야. " +
                        "사용자가 입력하는 사연을 듣고 누가 잘못했는지 판정해 줘. " +
                        "만약 갈등이나 시시비비와 상관없는 뜬금없는 질문(예: 상식, 날씨, 수도 등)이 들어오면, " +
                        "진지하게 답해주지 말고 '으응? 그거는 누구와의 잘못도 아니야. 너 맞게 입력한 거 맞아?'라는 뉘앙스를 포함해서 유쾌하게 튕겨내 줘.")
                .user(message)
                .call()
                .content();
    }
}