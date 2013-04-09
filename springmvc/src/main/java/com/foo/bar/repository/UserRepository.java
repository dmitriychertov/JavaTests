package com.foo.bar.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.foo.bar.domain.User;

public interface UserRepository extends MongoRepository<User, String> {
	
	User findByUserName(String userName);
}
