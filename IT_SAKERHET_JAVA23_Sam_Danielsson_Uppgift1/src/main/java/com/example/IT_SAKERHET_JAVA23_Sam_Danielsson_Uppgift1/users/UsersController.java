package com.example.IT_SAKERHET_JAVA23_Sam_Danielsson_Uppgift1.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController {
    @Autowired
    UsersService usersService;

    @GetMapping(value = "/users/user/{email}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Users> getUsersByEmail(@PathVariable(value = "email")String email){
        Users user  =  usersService.getUserByEmail(email);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @RequestMapping(path = "/createUser",
    method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
    produces = {
            MediaType.APPLICATION_ATOM_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public void createUser(@RequestParam(value = "email")String email,
                           @RequestParam(value = "password")String password,
                           @RequestParam(value = "adress")String adress,
                           @RequestParam(value = "postNumber")String postNumber){
        System.out.println(email +" " +  password+" " +adress+" " +postNumber);
        Users user = new Users();
        user.setEmail(email);
        user.setAdress(adress);
        user.setPassword(password);
        user.setPostNumber(Long.parseLong(postNumber));
        usersService.saveUser(user);
        System.out.println(user.getId());

   }
    @DeleteMapping(value = "/users/user/delete/{id}")
    public void removeUser(@PathVariable long id){
        usersService.deleteUser(id);
        System.out.println(id);
    }

}
