package com.midgard.ticket.requests;

import com.midgard.ticket.TicketCategories;
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
public class TicketCreateRequest {

    private String title;
    private String ownerEmail;
    private List<UserEntity> includedUsers;
    private String content;
    private List<TicketCategories> categories;
    private String priority;
}
