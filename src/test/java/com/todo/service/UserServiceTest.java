package com.todo.service;


import com.todo.domain.Users;
import com.todo.repository.UsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UsersRepository usersRepository;

    @Test
    public void shouldSaveTwoTimes() {
        Users users = new Users();
        users.setPassword("123");
        users.setId(1L);
        userService.save(users);
        verify(usersRepository, times(2)).save(users);
    }
}