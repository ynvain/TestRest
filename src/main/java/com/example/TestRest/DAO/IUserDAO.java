package com.example.TestRest.DAO;

import java.util.List;
import com.example.TestRest.Model.User;

public interface IUserDAO {

    List<User> getAllUsers();
    User getUserByName(String name);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(String name);
    boolean userExists(String name);

}
