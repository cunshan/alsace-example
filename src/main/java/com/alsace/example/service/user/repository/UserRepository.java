package com.alsace.example.service.user.repository;

import com.alsace.example.service.user.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByUserName(String userName);

}
