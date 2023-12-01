package com.midgard.ticket.requests;

import com.midgard.ticket.TicketCategories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketCategoriesRequest {

    private Long ticket_id;
    private List<TicketCategories> categories;
}
