package com.midgard.ticket.requests;

import com.midgard.ticket.TicketPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketPriorityRequest {

    private Long ticket_id;
    private TicketPriority priority;
}
