package com.trainbookingapp.net.service;

import com.trainbookingapp.net.model.User;
import com.trainbookingapp.net.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setId("1");
        user.setName("John Doe");

        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertEquals(user, savedUser);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetUserById() {
        String userId = "1";
        User user = new User();
        user.setId(userId);
        user.setName("John Doe");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(userId);

        assertEquals(user, foundUser);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("1", "John Doe", "John.Deo@gmail.com"));
        userList.add(new User("2", "Jane Smith", "Jane.Smith@gmail.com"));

        when(userRepository.findAll()).thenReturn(userList);

        Iterable<User> users = userService.getAllUsers();

        assertEquals(userList, (List<User>) users);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteUserById() {
        String userId = "1";

        userService.deleteUserById(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}
