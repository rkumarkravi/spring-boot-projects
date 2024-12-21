package com.egjpa.rkproj.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "IC_USERS")
@Builder
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "json_uid"
)
@ToString
public class User implements UserDetails {
    private final LocalDateTime createdOn = LocalDateTime.now();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;
    @Column(updatable = false, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    private String fullName;
    private String bio;
    private String profilePictureUrl;
    private String password;
    @OneToMany(mappedBy = "creator", orphanRemoval = true)
    @ToString.Exclude
    private Set<Post> createdPosts = new LinkedHashSet<>();
    private boolean isLocked=false;
    private boolean isCredentialsNonExpired=false;


    @ManyToMany
    @JoinTable(name = "JPA_USERS_SAVED_POSTS",
            joinColumns = @JoinColumn(name = "user_user_id"))
    @ToString.Exclude
    private Set<Post> savedPosts = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "likedBy")
    @ToString.Exclude
    private Set<Post> postsLiked = new LinkedHashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isLocked;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return !isLocked;
    }
}
