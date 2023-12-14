package com.midgard.ticket;

import com.midgard.comment.CommentEntity;
import com.midgard.user.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketUpdateRequest {

    private Long id;
    private String title;
    private UserEntity ownerUser;
    private List<UserEntity> includedUsers;
    private String content;
    private List<CommentEntity> comments;
    private TicketStatus status;
    private List<TicketCategories> categories;
    private TicketPriority priority;
    private LocalDateTime deadline;
}
