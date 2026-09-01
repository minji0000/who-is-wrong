package com.kari.whoiswrong.service;

import com.kari.whoiswrong.entity.Story;
import com.kari.whoiswrong.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoryService {

    @Autowired
    private StoryRepository storyRepository;

    public void saveStory(String content, String aiResult) {
        Story story = new Story();
        story.setContent(content);
        story.setAiResult(aiResult);
        storyRepository.save(story);
    }

    public List<Story> getRecentStories() {
        return storyRepository.findTop3ByOrderByIdDesc();
    }
}
