package com.example.TestRest.Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;


//class that binds rows in database to user variables
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet row, int rowNum) throws SQLException {
        User user = new User();
        user.setName(row.getString("name"));
        user.setSurname(row.getString("surname"));
        return user;
    }





}
