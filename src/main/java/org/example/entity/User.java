package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "USER")
@DiscriminatorValue("USER")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Integer userId;
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @Column(name = "USER_BIRTHDAY", nullable = true)
    private Date userBirthday;

    @Column(name = "USER_AVATAR_LINK", nullable = true)
    private String userAvatarlink;

    @Column(name = "USER_JOB", nullable = true)
    private String userJob;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    List<Status> statuses;

    public User(String email, String password, String userName, Date userBirthday, String userAvatarlink, String userJob) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.userBirthday = userBirthday;
        this.userAvatarlink = userAvatarlink;
        this.userJob = userJob;
    }

    public User(String email, String password, String userName, Date userBirthday) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.userBirthday = userBirthday;
    }
}