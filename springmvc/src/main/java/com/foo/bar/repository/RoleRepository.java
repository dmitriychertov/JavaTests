package com.foo.bar.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.foo.bar.domain.Role;

public interface RoleRepository extends MongoRepository<Role, String> {

}
