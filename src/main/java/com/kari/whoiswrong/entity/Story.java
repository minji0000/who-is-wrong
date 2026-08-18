package com.kari.whoiswrong.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content; // 사용자가 보낸 사연

    @Column(columnDefinition = "TEXT")
    private String aiResult; // AI가 판정한 결과

    private LocalDateTime createdAt; // 저장된 시간

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}