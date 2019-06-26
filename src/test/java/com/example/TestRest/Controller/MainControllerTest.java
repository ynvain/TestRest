package com.example.TestRest.Controller;

import com.example.TestRest.DAO.IUserDAO;
import com.example.TestRest.Model.User;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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

        when(userDAO.userExists((user.getName()))).thenReturn(true);
        when(userDAO.getUserByName((user.getName()))).thenReturn(user);

        ResponseEntity<User> res = controller.getUserByName(user.getName());

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> argumentCaptor1 = ArgumentCaptor.forClass(String.class);

        verify(userDAO).userExists(argumentCaptor.capture());
        verify(userDAO).getUserByName(argumentCaptor1.capture());

        assertEquals(user.getName(), argumentCaptor.getValue());
        assertEquals(user.getName(), argumentCaptor1.getValue());

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(user.getName(),res.getBody().getName());
        assertEquals(user.getSurname(), res.getBody().getSurname());

    }

    @Test
    public void getUserByNameFail() {

        user = new User();
        user.setName("Ivan");
        user.setSurname("Grigoriev");

        when(userDAO.userExists((user.getName()))).thenReturn(false);

        ResponseEntity<User> res = controller.getUserByName(user.getName());

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(userDAO).userExists(argumentCaptor.capture());
        verify(userDAO, never()).getUserByName(anyString());

        assertEquals(user.getName(), argumentCaptor.getValue());

        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
        assertNull(res.getBody());

    }

    @Test
    public void addUser() {

        user = new User();
        user.setName("Ivan");
        user.setSurname("Grigoriev");

        when(userDAO.userExists(user.getName())).thenReturn(false);

        ResponseEntity<Void> res = controller.addUser(user);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<User> argumentCaptor1 = ArgumentCaptor.forClass(User.class);

        verify(userDAO).userExists(argumentCaptor.capture());
        verify(userDAO).addUser(argumentCaptor1.capture());

        assertEquals(user.getName(), argumentCaptor.getValue());
        assertEquals(user.getName(), argumentCaptor1.getValue().getName());
        assertEquals(user.getSurname(), argumentCaptor1.getValue().getSurname());

        assertEquals(HttpStatus.CREATED, res.getStatusCode());

    }

    @Test
    public void deleteUser() {

        user = new User();
        user.setName("Ivan");
        user.setSurname("Grigoriev");

        when(userDAO.userExists(user.getName())).thenReturn(true);

        ResponseEntity<Void> res = controller.deleteUser(user.getName());

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> argumentCaptor1 = ArgumentCaptor.forClass(String.class);

        verify(userDAO).userExists(argumentCaptor.capture());
        verify(userDAO).deleteUser(argumentCaptor1.capture());

        assertEquals(user.getName(), argumentCaptor.getValue());
        assertEquals(user.getName(), argumentCaptor1.getValue());

        assertEquals(HttpStatus.NO_CONTENT, res.getStatusCode());

    }




}