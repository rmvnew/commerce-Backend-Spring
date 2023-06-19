package com.delta.commerce.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "User")
@Table(name = "tb_user")
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name", unique = true)
    private String userCompleteName;

    @Column(name = "user_email", unique = true)
    private String userEmail;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_enrollment", unique = true)
    private String userEnrollment;

    @Column(name = "user_recover_code", nullable = true)
    private String recoverCode;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_profile",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id"))
    private Set<Profile> profiles;

    @OneToMany(mappedBy = "user")
    private Set<Sale> sales;

    public User(String userCompleteName, String userEmail,
                String userPassword, String userEnrollment,
                boolean isActive,
                LocalDateTime createAt, Set<Profile> profiles) {
        this.userCompleteName = userCompleteName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userEnrollment = userEnrollment;
        this.isActive = isActive;
        this.createAt = createAt;
        this.profiles = profiles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return profiles.stream()
                .flatMap(profile -> profile.getTransactions().stream())
                .map(transaction -> new SimpleGrantedAuthority(transaction.getTransactionName()))
                .collect(Collectors.toList());
    }


    @Override
    public String getPassword() {
        return getUserPassword();
    }

    @Override
    public String getUsername() {
        return getUserEmail();
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
