package com.kari.whoiswrong.controller;

import com.kari.whoiswrong.entity.Story;
import com.kari.whoiswrong.service.StoryService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AiController {

    private final ChatClient chatClient;

    private final StoryService storyService;

    public AiController(ChatClient.Builder chatClientBuilder, StoryService storyService) {
        this.chatClient = chatClientBuilder.build();
        this.storyService = storyService;
    }

    @GetMapping("/ai")
    public ResponseEntity<String> ai(@RequestParam String message) {
        String aiResult;

        try {
            // 1. AI 판정 시도
            aiResult = chatClient
                    .prompt()
                    .system("너는 갈등 상황의 시시비비를 가려주는 판정사야. " +
                            "사용자가 입력하는 사연을 듣고 누가 잘못했는지 판정해 줘. " +
                            "만약 갈등이나 시시비비와 상관없는 뜬금없는 질문(예: 상식, 날씨, 수도 등)이 들어오면, " +
                            "진지하게 답해주지 말고 '으응? 그거는 누구와의 잘못도 아니야. 너 맞게 입력한 거 맞아?'라는 뉘앙스를 포함해서 유쾌하게 튕겨내 줘.")
                    .user(message)
                    .call()
                    .content();

            // 2. AI 호출 성공 시: 사연과 결과를 함께 저장
            storyService.saveStory(message, aiResult);

            return ResponseEntity.ok(aiResult);

        } catch (Exception e) {
            // 3. AI 호출 실패 시 (쿼터 초과 등): 사연만이라도 DB에 안전하게 저장!
            // (aiResult 자리에 에러 안내 문구나 빈 값을 넣어 저장할 수 있습니다)
            storyService.saveStory(message, "AI 판정 실패 (이용량 초과)");

            // 4. 프론트엔드(클라이언트)에는 친절한 에러 메시지 반환
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("AI 이용량이 초과되었습니다! 잠시 후(약 40초 뒤)에 다시 시도해주세요. (개발자가 가난해서... 무료버전..써서 그래..🥹)");
        }
    }

    @GetMapping("/api/stories/recent")
    public ResponseEntity<List<Story>> getRecentStories() {
        List<Story> recentStories = storyService.getRecentStories();
        return ResponseEntity.ok(recentStories);
    }
}