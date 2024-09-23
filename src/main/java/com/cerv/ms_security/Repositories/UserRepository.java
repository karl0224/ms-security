package com.cerv.ms_security.Repositories;

import com.cerv.ms_security.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
