package com.midgard.ticket.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketCategoriesResponse {
    private boolean update;
    private Long ticket_id;
}
