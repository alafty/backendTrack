package com.sumerge.alaftyBackend.Controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final static List<UserDetails> users = Arrays.asList(
            new User("Alafty", "al13", Collections.singleton(new SimpleGrantedAuthority("USER"))),
            new User("Youssef", "ye16", Collections.singleton(new SimpleGrantedAuthority("ADMIN")))
    );
    public PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = users
                .stream()
                .filter(userDetails -> userDetails.getUsername().equals(username))
                .findFirst().orElseThrow(() -> new UsernameNotFoundException("Username not Found"));
        return User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .authorities(user.getAuthorities())
                .build();
    }
}
