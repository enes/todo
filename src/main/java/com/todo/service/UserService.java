package com.todo.service;

import com.todo.component.EncyptPassword;
import com.todo.component.TokenUtils;
import com.todo.domain.Users;
import com.todo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    public void save(Users user) {
        user.setPassword(EncyptPassword.getMd5(user.getPassword()));
        usersRepository.save(user);
        user.setToken(TokenUtils.generateTokenFrom(user.getId()));
        usersRepository.save(user);
    }

    public Users findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public Users findByToken(String token) {
       return usersRepository.findByToken(token);
    }
}
