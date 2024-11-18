package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Table(name = "POST")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue
    @Column(name = "POST_ID")
    private Integer postId;

    @Column(name = "CONTENT", nullable = true)
    private String content;

    @Column(name = "CATEGORY", nullable = true)
    private String category;

    @Column(name = "DATE", nullable = true)
    private Date date;

    @Column(name = "VIEW_COUNT", nullable = true)
    private Long viewCount;
}
