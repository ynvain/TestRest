package com.example.TestRest.Controller;

import com.example.TestRest.DAO.IUserDAO;
import com.example.TestRest.Model.User;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MainControllerTest {

    @InjectMocks
    private MainController controller;

    @Mock
    private IUserDAO userDAO;


    private User user;

    @BeforeEach
    void setUp() throws Exception{

        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getUserByName() {

        user = new User();
        user.setName("Ivan");
        user.setSurname("Grigoriev");

        when(userDAO.getUserByName(anyString())).thenReturn(user);
        when(userDAO.userExists((anyString()))).thenReturn(true);

        ResponseEntity<User> res = controller.getUserByName("Ivan");

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(user.getName(),res.getBody().getName());
        assertEquals(user.getSurname(), res.getBody().getSurname());

    }

    @Test
    public void getUserByNameFail() {

        user = new User();
        user.setName("Ivan");
        user.setSurname("Grigoriev");

        when(userDAO.userExists((anyString()))).thenReturn(false);

        ResponseEntity<User> res = controller.getUserByName("Ivan");

        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
        assertNull(res.getBody());

    }

    @Test
    public void addUser() {

        user = new User();
        user.setName("Ivan");
        user.setSurname("Grigoriev");

        when(userDAO.userExists((anyString()))).thenReturn(false);

        ResponseEntity<Void> res = controller.addUser(user);
        assertEquals(HttpStatus.CREATED, res.getStatusCode());

    }

    @Test
    public void deleteUser() {

        when(userDAO.userExists((anyString()))).thenReturn(true);

        ResponseEntity<Void> res = controller.deleteUser(anyString());
        assertEquals(HttpStatus.NO_CONTENT, res.getStatusCode());

    }




}