package com.midgard.ticket.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.midgard.ticket.TicketCategories;
import com.midgard.ticket.TicketPriority;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketCreateRequest {

    private String title;
    private String ownerEmail;
    private List<UserEntity> includedUsers;
    private String content;
    private List<TicketCategories> categories;
    private TicketPriority priority;
    private LocalDateTime deadline;

    public void setIncludedUsers(List<UserEntity> users) {
        this.includedUsers = users;
    }

    public List<UserEntity> getIncludedUsers() {
        return includedUsers;
    }

    public List<TicketCategories> getCategories() {
        return categories;
    }

    public void setCategories(List<TicketCategories> categories) {
        this.categories = categories;
    }
}
