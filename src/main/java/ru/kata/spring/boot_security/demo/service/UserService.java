package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService {

        List<User> getUserList();

        void addUser(User user);

        void removeUser(int id);

        User getUserById(int id);

//        void updateUser(int id, User updated);

        void updateUser(User updated);

}
