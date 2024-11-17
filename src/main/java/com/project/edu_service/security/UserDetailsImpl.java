package com.project.edu_service.security;

import com.project.edu_service.entityes.Users;
import com.project.edu_service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class UserDetailsImpl implements UserDetails {

    private final Users users;

    public UserDetailsImpl(Users users) {
        this.users = users;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("User into UserDetailsImpl {}", users);
        return List.of(new SimpleGrantedAuthority(users.getRole().name()));
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUsername();
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
