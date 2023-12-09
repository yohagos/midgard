package com.midgard.ticket;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;


@RequiredArgsConstructor
public enum TicketStatus  implements Serializable {
    OPEN("OPEN"),
    IMPLEMENTING("IMPLEMENTING"),
    REVIEWING("REVIEWING"),
    CLOSED("CLOSED");

    @Getter
    private final String status;
}
