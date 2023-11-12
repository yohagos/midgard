package com.midgard.token;

import com.midgard.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

    @Id
    @GeneratedValue
    public Integer id;

    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;
    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserEntity user;

    public Token(String token, boolean revoked, boolean expired, UserEntity user) {
        this.token = token;
        this.tokenType = TokenType.BEARER;
        this.revoked = revoked;
        this.expired = expired;
        this.user = user;
    }
}
