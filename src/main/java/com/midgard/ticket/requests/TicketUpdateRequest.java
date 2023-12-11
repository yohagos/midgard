package com.midgard.ticket.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.midgard.comment.CommentEntity;
import com.midgard.ticket.TicketCategories;
import com.midgard.ticket.TicketPriority;
import com.midgard.ticket.TicketStatus;
import com.midgard.user.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketUpdateRequest {

    private Long id;
    private String title;
    private UserEntity ownerUser;
    private List<UserEntity> includedUsers;
    private String content;
    private List<TicketCategories> categories;
    private TicketStatus status;
    private TicketPriority priority;

    public void setIncludedUsers(List<UserEntity> users) {
        this.includedUsers = users;
    }

    public void setCategories(List<TicketCategories> categories) {
        this.categories = categories;
    }

    public List<UserEntity> getIncludedUsers() {
        return includedUsers;
    }

    public List<TicketCategories> getCategories() {
        return categories;
    }
}
