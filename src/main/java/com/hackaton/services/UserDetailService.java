package com.hackaton.services;

import com.hackaton.models.User;
import com.hackaton.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public UserDetailService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByLogin(login);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Неверный пароль или логин");
        }

        return new com.hackaton.sequrity.UserDetails(user.get());
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Неверный пароль или логин");
        }

        return new com.hackaton.sequrity.UserDetails(user.get());

    }
}
