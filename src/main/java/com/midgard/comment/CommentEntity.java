package com.midgard.comment;

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

    @Column(
            name = "ticket_id",
            nullable = false
    )
    private Long ticketId;

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

    public CommentEntity(Long ticketId, UserEntity user, String content) {
        this.ticketId = ticketId;
        this.user = user;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public CommentEntity(Long id, Long ticketId, UserEntity user, String content, LocalDateTime timestamp) {
        this.id = id;
        this.ticketId = ticketId;
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

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
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
                "Comment=[ticketID=%s, owner=%s, content=%s]",
                getTicketId(), getUser(), getContent()
        );
    }
}
