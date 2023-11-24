package com.midgard.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentEditRequest {

    private String content;
    private Long ticket_id;
    private Long comment_id;
}
