package com.todo.repository;

import com.todo.domain.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, Long> {

     Users findByEmail(String email);

     Users findByToken(String token);

}
