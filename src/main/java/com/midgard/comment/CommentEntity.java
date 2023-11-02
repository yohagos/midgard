package com.midgard.comment;

import com.midgard.ticket.TicketEntity;
import com.midgard.user.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

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

    public CommentEntity() {

    }

    public CommentEntity(TicketEntity ticket, UserEntity user, String content) {
        this.ticket = ticket;
        this.user = user;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public CommentEntity(Long id, TicketEntity ticket, UserEntity user, String content, LocalDateTime timestamp) {
        this.id = id;
        this.ticket = ticket;
        this.user = user;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TicketEntity getTicket() {
        return ticket;
    }

    public void setTicket(TicketEntity ticket) {
        this.ticket = ticket;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format(
                "Comment=[ticket=%s, comment=%s, content=%s]",
                getTicket(), getUser(), getContent()
        );
    }
}
