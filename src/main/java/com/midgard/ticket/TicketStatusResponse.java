package com.midgard.ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketStatusResponse {

    private String changedTo;
    private Long ticket_id;
    private boolean changed;
}
