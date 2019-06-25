package com.example.TestRest.Controller;

import com.example.TestRest.DAO.IUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.TestRest.Model.User;
import org.springframework.web.util.UriComponentsBuilder;

//main controller that receives requests

@RestController
@RequestMapping("users")
public class MainController
{

    //injecting necessary data access object
    @Autowired
    private IUserDAO userDAO;

    //returns all users from the database
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers()
    {
        List<User> list = userDAO.getAllUsers();
        return new ResponseEntity<List<User>>(list, HttpStatus.OK);
    }


    //returns a single user from the database
    @GetMapping("{name}")
    public ResponseEntity<User> getUserByName(@PathVariable("name") String name) {
        if(userDAO.userExists(name)){
            User user = userDAO.getUserByName(name);

            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    //adds a new user
    @PostMapping
    public ResponseEntity<Void> addUser(@RequestBody User user) {

        if(!userDAO.userExists(user.getName())){
            userDAO.addUser(user);

            return new ResponseEntity<Void>(HttpStatus.CREATED);
        }
        else return new ResponseEntity<Void>(HttpStatus.CONFLICT);

    }

    //updates a surname of a single user
    @PutMapping("{name}")
    public ResponseEntity<User> updateUser(@RequestBody User user) {

        if(userDAO.userExists(user.getName())){
            userDAO.updateUser(user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    //deletes requested user
    @DeleteMapping("{name}")
    public ResponseEntity<Void> deleteUser(@PathVariable("name") String name) {

        if(userDAO.userExists(name)){
            userDAO.deleteUser(name);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }






}
