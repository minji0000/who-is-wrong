package com.kari.whoiswrong.repository;

import com.kari.whoiswrong.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long> {
    // 최신 순으로 최대 3개 조회
    List<Story> findTop3ByOrderByIdDesc();
}