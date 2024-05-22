package org.example.service;

import org.example.db.doa.UserDao;
import org.example.domain.User;
import org.jdbi.v3.core.Jdbi;

import java.util.Arrays;
import java.util.List;

public class UserService {

    private final Jdbi jdbi;
    private final UserDao userDao;

    public UserService(Jdbi jdbi) {
        this.jdbi = jdbi;
        this.userDao = new UserDao(jdbi);
    }

    public List<User> getAllUser() {
        return userDao.getAll();
    }

    public void addUser(User user) {
        userDao.addUser(user);
    }

    public Boolean existUser(int userId) {
        var user = userDao.existUser(userId);
        return user != 0;
    }
}
