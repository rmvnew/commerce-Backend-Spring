package com.delta.commerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "User")
@Table(name = "tb_user")
@NoArgsConstructor
@Getter
@Setter
public class User {

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

    @Column(name = "user_enrollment")
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
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<Profile> profiles;

    public User(String userCompleteName, String userEmail,
                String userPassword, String userEnrollment,
                String recoverCode, boolean isActive,
                LocalDateTime createAt, Set<Profile> profiles) {
        this.userCompleteName = userCompleteName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userEnrollment = userEnrollment;
        this.recoverCode = recoverCode;
        this.isActive = isActive;
        this.createAt = createAt;
        this.profiles = profiles;
    }
}
