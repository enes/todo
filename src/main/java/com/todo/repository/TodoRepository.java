package com.todo.repository;

import com.todo.domain.Todos;
import com.todo.domain.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoRepository extends CrudRepository<Todos, Long> {
    List<Todos> findByUser(Users user);

    Todos findByIdAndUser(Long id, Users user);

}
