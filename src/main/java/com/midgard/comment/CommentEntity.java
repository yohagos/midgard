package com.midgard.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.midgard.ticket.TicketEntity;
import com.midgard.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentEntity  implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private TicketEntity ticket;

    @ManyToOne
    private UserEntity user;

    private String content;
    private LocalDateTime timestamp;

    public CommentEntity(TicketEntity ticket, UserEntity user, String content) {
        this.ticket = ticket;
        this.user = user;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format(
                "Comment=[ticket=%s, comment=%s, content=%s]",
                getTicket(), getUser(), getContent()
        );
    }
}
