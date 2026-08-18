package com.kari.whoiswrong.repository;

import com.kari.whoiswrong.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, Long> {
}