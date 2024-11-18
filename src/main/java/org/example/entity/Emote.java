package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Table(name = "EMOTE")
@Entity
@Getter
@Setter
public class Emote {
    @Id
    @GeneratedValue
    @Column(name = "EMOTE_ID")
    private Integer emoteId;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID", nullable = true)
    Comment comment;

    @ManyToOne
    @JoinColumn(name = "STATUS_ID", nullable = true)
    Status status;

    @Column(name = "DATE", nullable = true)
    Date date;

    public Emote() {
    }

    public Emote(Integer emoteId, Date date) {
        this.emoteId = emoteId;
        this.user = user;
        this.comment = comment;
        this.status = status;
        this.date = date;
    }
}
