package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Table(name = "STATUS")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Status {
    @Id
    @GeneratedValue
    @Column(name = "STATUS_ID")
    private Integer statusId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "CONTENT", nullable = true)
    private String content;

    @Column(name = "IMAGE_LINK", nullable = true)
    private String imageLink;

    @Column(name = "DATE", nullable = true)
    private Date date;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<Emote> emotes;
}
