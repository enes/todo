package com.todo.service;

import com.todo.domain.Todos;
import com.todo.domain.Users;
import com.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public void save(Todos todo) {
        todoRepository.save(todo);
    }

    public List<Todos> findByUser(Users user) {
        return todoRepository.findByUser(user);
    }

    public Todos findByIdAndUser(Long id, Users user) {
        return todoRepository.findByIdAndUser(id, user);
    }

    public void delete(Todos todo) {
        todoRepository.delete(todo);
    }

}
