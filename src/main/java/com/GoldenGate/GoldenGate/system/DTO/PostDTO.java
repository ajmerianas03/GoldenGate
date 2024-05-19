package com.GoldenGate.GoldenGate.system.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class PostDTO {

    private Long postId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private int commentsCount;
    private int likesCount;
    private List<String> images;
    boolean post_Like_Status;

}
