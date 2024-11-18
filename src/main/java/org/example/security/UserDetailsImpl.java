package org.example.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;
@Data
@NoArgsConstructor
@Builder
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String email;

    @JsonIgnore
    private String password;

    private String username;

    private Date userBirthday;

    private String userAvtarLink;

    private String job;

    public UserDetailsImpl(Integer id, String email, String password, String username,
                           Date userBirthday, String userAvtarLink, String job) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.userBirthday = userBirthday;
        this.userAvtarLink = userAvtarLink;
        this.job = job;
    }

    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
                user.getUserName(),
                user.getUserBirthday(),
                user.getUserAvatarlink(),
                user.getUserJob());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
