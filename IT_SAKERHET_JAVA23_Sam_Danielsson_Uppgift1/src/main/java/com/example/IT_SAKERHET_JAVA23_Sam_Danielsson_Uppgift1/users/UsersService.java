package com.example.IT_SAKERHET_JAVA23_Sam_Danielsson_Uppgift1.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {
    @Autowired
    UserRepository userRepository;

    public Users getUserByEmail(String email){
        Users user = userRepository.findUsersByEmail(email);

        System.out.println(user);
        return user;

    }

    public Users saveUser(Users user){
        return userRepository.save(user);
    }

    public void deleteUser(long id){
        userRepository.deleteById(id);
    }

}
