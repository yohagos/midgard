package com.midgard.ticket;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;


@RequiredArgsConstructor
public enum TicketStatus  implements Serializable {
    OPEN("open"),
    IMPLEMENTING("implementing"),
    REVIEWING("reviewing"),
    CLOSED("closed");

    @Getter
    private final String status;
}
