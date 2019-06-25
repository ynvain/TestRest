package com.example.TestRest.DAO;

import com.example.TestRest.DAO.IUserDAO;
import com.example.TestRest.Model.User;
import com.example.TestRest.Model.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
        import org.springframework.stereotype.Repository;
        import org.springframework.transaction.annotation.Transactional;

        import java.util.List;

@Transactional
@Repository

//class that works with the database
public class UserDAO implements IUserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getAllUsers()
    {
        String sql = "SELECT name, surname FROM users";

        RowMapper<User> rowMapper = new UserRowMapper();
        return this.jdbcTemplate.query(sql, rowMapper);
    }
    @Override
    public User getUserByName(String name){
        String sql = "SELECT name, surname FROM users WHERE name=?";
        RowMapper<User> rowMapper = new UserRowMapper();
        User user = jdbcTemplate.queryForObject(sql, rowMapper, name);
        return user;
    }

    @Override
    public void addUser(User user){

        String sql = "INSERT INTO users (name, surname) values (?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getSurname());
    }


    @Override
    public void updateUser(User user) {
        String sql = "UPDATE users SET surname=? WHERE name=?";
        jdbcTemplate.update(sql, user.getSurname(), user.getName());
    }

    @Override
    public void deleteUser(String name) {
        String sql = "DELETE FROM users WHERE name=?";
        jdbcTemplate.update(sql, name);
    }

    //checks if a certain user is in db
    @Override
    public boolean userExists(String name){

        String sql = "SELECT count(*) FROM users WHERE name=?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, name);
        if(count == 0) {
            return false;
        } else {
            return true;
        }

    }


}