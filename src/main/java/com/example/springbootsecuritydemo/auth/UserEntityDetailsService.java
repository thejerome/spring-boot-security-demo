package com.example.springbootsecuritydemo.auth;

import com.example.springbootsecuritydemo.entities.UserEntity;
import com.example.springbootsecuritydemo.repo.UserRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserEntityDetailsService implements UserDetailsService {

    private final UserRepo userRepo;
    private PasswordEncoder encoder;

    public UserEntityDetailsService(final UserRepo userRepo, final PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        try {
            UserEntity candidate = userRepo.findByUsername(username);

            if (candidate != null) {
                return User.withUsername(candidate.getUsername())
                        .password(candidate.getPassword())
                        .roles("USER")
                        .build();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        throw new UsernameNotFoundException(username);
    }
}
