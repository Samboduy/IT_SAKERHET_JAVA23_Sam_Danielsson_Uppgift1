package com.example.IT_SAKERHET_JAVA23_Sam_Danielsson_Uppgift1.users;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<Users,Integer> {
    Users findUsersByEmail(String email);
}
