package com.trainbookingapp.net.repository;

import com.trainbookingapp.net.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}

