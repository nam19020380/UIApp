package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Table(name = "COMMENT")
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "COMMENT_ID")
    private Integer commentId;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "PARENT_COMMENT_ID", nullable = true)
    Comment comment;

    @ManyToOne
    @JoinColumn(name = "STATUS_ID", nullable = true)
    Status status;

    @Column(name = "CONTENT", nullable = true)
    String content;

    @Column(name = "IMAGE_LINK", nullable = true)
    String imageLink;

    @Column(name = "DATE", nullable = true)
    Date date;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    List<Comment> comments;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    List<Emote> emotes;
}
