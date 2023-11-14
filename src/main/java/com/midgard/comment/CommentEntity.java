package com.midgard.comment;

import com.midgard.ticket.TicketEntity;
import com.midgard.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class CommentEntity {

    @Id
    @SequenceGenerator(
            name = "comment_sequence",
            sequenceName = "comment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "comment_sequence"
    )
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    private TicketEntity ticket;

    @OneToOne
    private UserEntity user;

    @Column(
            name = "content",
            nullable = false
    )
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
