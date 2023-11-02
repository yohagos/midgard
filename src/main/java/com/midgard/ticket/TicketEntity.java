package com.midgard.ticket;

import com.midgard.comment.CommentEntity;
import com.midgard.user.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tickets")
public class TicketEntity {

    @Id
    @SequenceGenerator(
            name = "ticket_sequence",
            sequenceName = "ticket_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ticket_sequence"
    )
    private Long id;

    @Column(name = "title")
    private String title;


    @OneToOne
    private UserEntity owner;


    @OneToMany
    private List<UserEntity> includedUsers;

    @Column(name = "content_text")
    private String content;

    @Column(name = "created_timestamp")
    private LocalDateTime createdAt;

    @Column(name = "closed_timestamp")
    private LocalDateTime closedAt;

    @OneToMany
    private List<CommentEntity> comments;

    public TicketEntity() {

    }

    public TicketEntity(String title, UserEntity owner, List<UserEntity> includedUsers, String content) {
        this.title = title;
        this.owner = owner;
        this.includedUsers = includedUsers;
        this.content = content;
    }

    public TicketEntity(Long id, String title, UserEntity owner, String content) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.includedUsers = new ArrayList<>();
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public List<UserEntity> getIncludedUsers() {
        return includedUsers;
    }

    public void setIncludedUsers(List<UserEntity> includedUsers) {
        this.includedUsers = includedUsers;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        var includeUsers = getIncludedUsers();
        return String.format("Ticket=[id=%s, title=%s, owner=%s, content=%s]",
                getId(), getTitle(), getOwner(), getContent());
    }
}
