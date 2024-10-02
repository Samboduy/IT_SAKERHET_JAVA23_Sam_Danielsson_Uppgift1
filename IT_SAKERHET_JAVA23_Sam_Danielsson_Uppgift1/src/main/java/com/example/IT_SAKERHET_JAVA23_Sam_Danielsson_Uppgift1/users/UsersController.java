package com.example.IT_SAKERHET_JAVA23_Sam_Danielsson_Uppgift1.users;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public void createUser(@RequestParam String email, String password,String adress,String postNumber) {
        /*Users user = new Users();
        user.setEmail(userObject.getString("email"));
        user.setPassword(userObject.getString("password"));
       String adress = userObject.getString("adress");
       String postNumber = userObject.getString("postNumber");
        if (!adress.isEmpty() && !postNumber.isEmpty()){
            user.setAdress(adress);
            user.setPostNumber(Long.parseLong(postNumber));
        }

       System.out.println(user);
       Users savedUser = usersService.saveUser(user);
       System.out.println("saved user: " + savedUser);
       return new ResponseEntity<>(savedUser,HttpStatus.OK);*/
        System.out.println(email +" " +  password+" " +adress+" " +postNumber);

   }

}
