package com.kari.whoiswrong;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WhoIsWrongApplicationTests {

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    @Test
    void geminiTest() {

        ChatClient chatClient = chatClientBuilder.build();

        String response = chatClient
                .prompt()
                .user("안녕하세요. 간단하게 자기소개 해주세요.")
                .call()
                .content();

        System.out.println("================================");
        System.out.println("Gemini 응답:");
        System.out.println(response);
        System.out.println("================================");
    }
}