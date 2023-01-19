package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserDAO {

    List<User> getUserList();

    void addUser(User user);

    void removeUser(int id);

    User getUserById(int id);

    User updateUser(int id, User updated);

    public User getUserByName(String name);

}
