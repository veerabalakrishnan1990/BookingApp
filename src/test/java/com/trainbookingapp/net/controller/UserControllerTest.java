package com.trainbookingapp.net.controller;

import com.trainbookingapp.net.model.User;
import com.trainbookingapp.net.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setId("1");
        user.setName("John Doe");

        when(userService.saveUser(user)).thenReturn(user);

        User createdUser = userController.createUser(user);

        assertEquals(user, createdUser);
        verify(userService, times(1)).saveUser(user);
    }

    @Test
    public void testGetUserById() {
        String userId = "1";
        User user = new User();
        user.setId(userId);
        user.setName("John Doe");

        when(userService.getUserById(userId)).thenReturn(user);

        User foundUser = userController.getUserById(userId);

        assertEquals(user, foundUser);
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    public void testGetAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("1", "John Doe", "John.Deo@gmail.com"));
        userList.add(new User("2", "Jane Smith", "Jane.Smith@gmail.com"));

        when(userService.getAllUsers()).thenReturn(userList);

        List<Object> users = userController.getAllUsers();

        assertEquals(Collections.singletonList(userList), users);
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    public void testDeleteUserById() {
        String userId = "1";

        userController.deleteUserById(userId);

        verify(userService, times(1)).deleteUserById(userId);
    }
}

