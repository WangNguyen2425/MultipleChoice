package com.demo.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "token")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "token")
    private String token;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TokenType type;

    @Column(name = "is_expired")
    private boolean isExpired;

    @Column(name = "is_revoked")
    private boolean isRevoked;

    @PrePersist
    private void prePersist(){
        type = TokenType.BEARER;
        isRevoked = false;
        isExpired = false;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public enum TokenType{
        BEARER
    }
}
