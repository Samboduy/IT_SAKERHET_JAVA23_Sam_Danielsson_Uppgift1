package com.example.IT_SAKERHET_JAVA23_Sam_Danielsson_Uppgift1.users;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users,Integer> {
    Users findUsersByEmail(String email);
    void deleteById(long id);
}
