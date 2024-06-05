package com.jwtdemo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CollectionId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private int id;
    private String firstName;
    private String lastName;
    private Long mobileNo;
    private Long alternativeNo;
    private String email;
    private String password;

    private String collegeName;
    private String collegeLocation;
    private String degree;
    private String stream;
    private String percentage;
    private String passingYear;
    private Boolean arrears;

    private String location;
    private int experience;
    private String languages;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isEnabled;

    @Column(name = "verification_code",length = 64)
    private String verificationCode;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
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
