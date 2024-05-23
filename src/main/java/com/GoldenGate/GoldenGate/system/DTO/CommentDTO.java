package com.GoldenGate.GoldenGate.system.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long commentId;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Long parentCommentId;
    private Long postId;
    private Integer userId;
    private String avatar;
    private String fullName;
    private Integer profileId;
}
