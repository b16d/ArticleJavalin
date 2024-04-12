package org.example.service;

import org.example.domain.User;

import java.util.Arrays;
import java.util.List;

public class UserService {

    public List<User> getAllUser() {

        return Arrays.asList(
                new User("Bob", "Eponge"),
                new User("Luke", "Skywalker"),
                new User( "Captain", "Harlock"));
    }

    public void addUser(User user) {

    }
}
